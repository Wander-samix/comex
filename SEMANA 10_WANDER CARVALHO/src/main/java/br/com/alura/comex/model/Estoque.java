package br.com.alura.comex.model;

import jakarta.persistence.*;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Entity
@Table(name = "estoque")
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "estoque_itens", joinColumns = @JoinColumn(name = "estoque_id"))
    @MapKeyColumn(name = "produto_id")
    @Column(name = "quantidade")
    private Map<Long, Integer> produtos = new HashMap<>();

    public Estoque() {}

    public void adicionarProduto(Long produtoId, int quantidade) {
        this.produtos.merge(produtoId, quantidade, Integer::sum);
    }

    public boolean removerProduto(Long produtoId, int quantidade) {
        Integer atual = produtos.get(produtoId);
        if (atual == null || atual < quantidade) {
            return false;
        }
        produtos.put(produtoId, atual - quantidade);
        return true;
    }

    public int consultarQuantidade(Long produtoId) {
        return produtos.getOrDefault(produtoId, 0);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Map<Long, Integer> getProdutos() { return produtos; }
    public void setProdutos(Map<Long, Integer> produtos) { this.produtos = produtos; }
}
