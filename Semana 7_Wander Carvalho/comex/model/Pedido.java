package br.com.alura.comex.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Tipo alterado para Long, que é mais comum para IDs

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPedido> itensPedido;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDesconto tipoDesconto;

    // Construtores
    public Pedido(int id, Cliente cliente, BigDecimal preco, int quantidade, LocalDate data, TipoDesconto tipoDesconto) {
        this.id = (long) id;
        this.cliente = cliente;
        this.preco = preco;
        this.quantidade = quantidade;
        this.data = data;
        this.tipoDesconto = tipoDesconto;
    }

    public Pedido(int id, Cliente cliente, BigDecimal preco, int quantidade, int id1) {
        this.id = (long) id1;
    }

    public Pedido(String categoria, String produto, String cliente, BigDecimal preco, int quantidade, LocalDate data, int id) {
        this.id = (long) id;
    }

    public Pedido(String categoria, String produto, String cliente, BigDecimal preco, int quantidade, LocalDate data) {
    }

    public Pedido(int i, Cliente cliente, BigDecimal bigDecimal, int i1) {
    }

    // Getters e Setters
    public int getId() {
        return Math.toIntExact(id);
    }

    public void setId(int id) {
        this.id = (long) id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public TipoDesconto getTipoDesconto() {
        return tipoDesconto;
    }

    public void setTipoDesconto(TipoDesconto tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
    }

    // Método para calcular o valor total do pedido com desconto aplicado
    public BigDecimal getValorTotal() {
        BigDecimal total = preco.multiply(BigDecimal.valueOf(quantidade));
        if (tipoDesconto == TipoDesconto.FIDELIDADE) {
            return total.multiply(BigDecimal.valueOf(0.90)); // Aplica 10% de desconto
        }
        return total;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + (cliente != null ? cliente.getNome() : "N/D") +
                ", preço=" + preco +
                ", quantidade=" + quantidade +
                ", data=" + data +
                ", tipoDesconto=" + tipoDesconto +
                ", valor total=" + getValorTotal() +
                '}';
    }

    public boolean isMaisCaroQue(Pedido pedido2) {
        return false;
    }

    public boolean isMaisBaratoQue(Pedido pedido2) {
        return false;
    }
}
