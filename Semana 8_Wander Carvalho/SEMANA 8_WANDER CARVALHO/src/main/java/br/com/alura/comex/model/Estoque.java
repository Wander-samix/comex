package br.com.alura.comex.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "estoque")
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mapa de produtos e suas quantidades em estoque
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "estoque_itens", joinColumns = @JoinColumn(name = "estoque_id"))
    @MapKeyColumn(name = "produto_id")
    @Column(name = "quantidade")
    private Map<Long, Integer> produtos;

    public Estoque() {
        this.produtos = new HashMap<>();
    }

    /**
     * Adiciona produtos ao estoque.
     * @param produtoId o ID do produto
     * @param quantidade a quantidade a ser adicionada
     */
    public void adicionarProduto(Long produtoId, int quantidade) {
        this.produtos.merge(produtoId, quantidade, Integer::sum);
    }

    /**
     * Remove produtos do estoque.
     * @param produtoId o ID do produto
     * @param quantidade a quantidade a ser removida
     * @return true se a operação foi bem sucedida, false caso contrário (e.g., estoque insuficiente)
     */
    public boolean removerProduto(Long produtoId, int quantidade) {
        Integer atual = produtos.get(produtoId);
        if (atual == null || atual < quantidade) {
            return false; // Não é possível remover mais produtos do que o disponível
        }
        produtos.put(produtoId, atual - quantidade);
        return true;
    }

    /**
     * Retorna a quantidade disponível de um produto no estoque.
     * @param produtoId o ID do produto
     * @return a quantidade disponível
     */
    public int consultarQuantidade(Long produtoId) {
        return produtos.getOrDefault(produtoId, 0);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Long, Integer> getProdutos() {
        return produtos;
    }

    public void setProdutos(Map<Long, Integer> produtos) {
        this.produtos = produtos;
    }
}

