package br.com.alura.comex.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itensPedidos = new ArrayList<>();

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
    public Pedido() {
    }

    public Pedido(Cliente cliente, LocalDate data, TipoDesconto tipoDesconto) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        this.cliente = cliente;

        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula.");
        }
        this.data = data;

        if (tipoDesconto == null) {
            throw new IllegalArgumentException("Tipo de desconto não pode ser nulo.");
        }
        this.tipoDesconto = tipoDesconto;

        this.preco = BigDecimal.ZERO;
        this.quantidade = 0;
    }

    public Pedido(Cliente cliente, BigDecimal multiply, int quantidade, LocalDate now, TipoDesconto tipoDesconto, List<ItemPedido> itensPedido) {
    }

    // Método de criação do pedido
    public static Pedido createPedido(Cliente cliente, LocalDate data, TipoDesconto tipoDesconto) {
        return new Pedido(cliente, data, tipoDesconto);
    }

    public static Pedido createPedido(String categoria, String produto, String cliente, BigDecimal preco, int quantidade, LocalDate data) {
        return null;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<ItemPedido> getItensPedidos() {
        return itensPedidos;
    }

    public void setItensPedidos(List<ItemPedido> itensPedidos) {
        this.itensPedidos = itensPedidos;
    }

    public void adicionarItem(ItemPedido item) {
        item.setPedido(this);
        this.itensPedidos.add(item);
        atualizarPrecoEQuantidade();
    }

    private void atualizarPrecoEQuantidade() {
        this.preco = BigDecimal.ZERO;
        this.quantidade = 0;
        for (ItemPedido item : itensPedidos) {
            this.preco = this.preco.add(item.calcularValorTotal());
            this.quantidade += item.getQuantidade();
        }
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

    public BigDecimal getValorTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : itensPedidos) {
            if (item.getPrecoUnitario() == null || item.getTipoDesconto() == null) {
                throw new IllegalStateException("Preço ou tipo de desconto não pode ser nulo para calcular o valor total.");
            }
            total = total.add(item.calcularValorTotal());
        }
        return total;
    }

    public boolean isMaisCaroQue(Pedido pedido2) {
        return this.getValorTotal().compareTo(pedido2.getValorTotal()) > 0;
    }

    public boolean isMaisBaratoQue(Pedido pedido2) {
        return this.getValorTotal().compareTo(pedido2.getValorTotal()) < 0;
    }
}
