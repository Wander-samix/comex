package br.com.alura.comex.model;

import jakarta.persistence.*;
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
    @Column(name = "tipo_desconto", nullable = false)
    private TipoDesconto tipoDesconto = TipoDesconto.NENHUM; // Valor padrão

    public Pedido() {}

    public Pedido(Cliente cliente, LocalDate data, TipoDesconto tipoDesconto) {
        if (cliente == null) throw new IllegalArgumentException("Cliente não pode ser nulo.");
        if (data == null) throw new IllegalArgumentException("Data não pode ser nula.");
        if (tipoDesconto == null) throw new IllegalArgumentException("Tipo de desconto não pode ser nulo.");
        this.cliente = cliente;
        this.data = data;
        this.tipoDesconto = tipoDesconto;
        this.preco = BigDecimal.ZERO;
        this.quantidade = 0;
    }

    // Getters e Setters

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

    public String getId() {
        return "";
    }

    public String getData() {
        return "";
    }

    public Iterable<Object> getItensPedidos() {
        return null;
    }
}
