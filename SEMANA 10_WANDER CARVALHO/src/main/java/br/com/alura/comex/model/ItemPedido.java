package br.com.alura.comex.model;

import java.math.BigDecimal;

import br.com.alura.comex.ComexApplication;
import jakarta.persistence.*;

@Entity
@Table(name = "item_pedido")
public class ItemPedido extends ComexApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal desconto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDesconto tipoDesconto;

    public ItemPedido() {
        this.precoUnitario = BigDecimal.ZERO;
        this.desconto = BigDecimal.ZERO;
        this.tipoDesconto = TipoDesconto.NENHUM;
    }

    public ItemPedido(Produto produto, int quantidade, BigDecimal precoUnitario, TipoDesconto tipoDesconto, Pedido pedido) {
        if (produto == null) throw new IllegalArgumentException("Produto não pode ser nulo.");
        if (pedido == null) throw new IllegalArgumentException("Pedido não pode ser nulo.");
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario != null ? precoUnitario : BigDecimal.ZERO;
        this.desconto = BigDecimal.ZERO;
        this.tipoDesconto = tipoDesconto != null ? tipoDesconto : TipoDesconto.NENHUM;
        this.pedido = pedido;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public BigDecimal getDesconto() { return desconto; }
    public void setDesconto(BigDecimal desconto) { this.desconto = desconto; }

    public TipoDesconto getTipoDesconto() { return tipoDesconto; }
    public void setTipoDesconto(TipoDesconto tipoDesconto) { this.tipoDesconto = tipoDesconto; }

    public String getNomeProduto() {
        return produto != null ? produto.getNome() : "Produto não definido";
    }

    public BigDecimal calcularValorTotal() {
        BigDecimal total = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
        BigDecimal valorDesconto = total.multiply(BigDecimal.valueOf(tipoDesconto.getPercentual()));
        return total.subtract(valorDesconto);
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "id=" + id +
                ", produto=" + (produto != null ? produto.getNome() : "Produto não definido") +
                ", quantidade=" + quantidade +
                ", precoUnitario=" + precoUnitario +
                ", desconto=" + desconto +
                ", tipoDesconto=" + tipoDesconto +
                '}';
    }
}
