package br.com.alura.comex.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 220, nullable = false)
    private String nome;

    @Column(name = "descricao", length = 220, nullable = false)
    private String descricao;

    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal precoUnitario;

    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "categoria_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_categoria_produto"))
    private Categoria categoria;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPedido> itensPedido;

    // Construtor vazio para JPA
    public Produto() {}

    // Construtor principal
    public Produto(String nome, String descricao, BigDecimal precoUnitario, int quantidade, Categoria categoria) {
        setNome(nome);
        setDescricao(descricao);
        setPrecoUnitario(precoUnitario);
        setQuantidade(quantidade);
        setCategoria(categoria);
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public List<ItemPedido> getItensPedido() { return itensPedido; }
    public void setItensPedido(List<ItemPedido> itensPedido) { this.itensPedido = itensPedido; }

    // Override equals, hashCode e toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return quantidade == produto.quantidade &&
                Objects.equals(id, produto.id) &&
                Objects.equals(nome, produto.nome) &&
                Objects.equals(descricao, produto.descricao) &&
                Objects.equals(precoUnitario, produto.precoUnitario) &&
                Objects.equals(categoria, produto.categoria) &&
                Objects.equals(itensPedido, produto.itensPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao, precoUnitario, quantidade, categoria, itensPedido);
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", precoUnitario=" + precoUnitario +
                ", quantidade=" + quantidade +
                ", categoria=" + categoria +
                ", itensPedido=" + itensPedido +
                '}';
    }

    public void imprimirDetalhes() {
        System.out.println("Produto: " + nome);
        System.out.println("Descrição: " + descricao);
        System.out.println("Preço Unitário: R$ " + precoUnitario);
        System.out.println("Quantidade em Estoque: " + quantidade);
        System.out.println("Categoria: " + categoria);
        System.out.println("Itens do Pedido: " + itensPedido);
    }

    public Long longValue() {
        return id != null ? id.longValue() : null;
    }
}
