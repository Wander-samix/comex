package br.com.alura.comex.pedido;

//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//public class Pedido {
//
//    private String categoria;
//    private String produto;
//    private String cliente;
//
//    private BigDecimal preco;
//    private int quantidade;
//
//    private LocalDate data;
//
//    public Pedido(String categoria, String produto, String cliente, BigDecimal preco, int quantidade, LocalDate data) {
//        this.categoria = categoria;
//        this.produto = produto;
//        this.cliente = cliente;
//        this.preco = preco;
//        this.quantidade = quantidade;
//        this.data = data;
//    }
//
//    public String getCategoria() {
//        return categoria;
//    }
//
//    public String getProduto() {
//        return produto;
//    }
//
//    public String getCliente() {
//        return cliente;
//    }
//
//    public BigDecimal getPreco() {
//        return preco;
//    }
//
//    public int getQuantidade() {
//        return quantidade;
//    }
//
//    public LocalDate getData() {
//        return data;
//    }
//
//    @Override
//    public String toString() {
//        return "Pedido{" +
//                "categoria='" + categoria + '\'' +
//                ", produto='" + produto + '\'' +
//                ", cliente='" + cliente + '\'' +
//                ", preco=" + preco +
//                ", quantidade=" + quantidade +
//                ", data=" + data +
//                '}';
//    }
//
//}

import br.com.alura.comex.cliente.Cliente;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pedido {
    // Atributos privados
    private int id;
    private Cliente cliente;
    private BigDecimal preco;
    private int quantidade;

    // Construtor
    public Pedido(int id, Cliente cliente, BigDecimal preco, int quantidade) {
        this.id = id;
        this.cliente = cliente;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Pedido(String categoria, String produto, String cliente, BigDecimal preco, int quantidade, LocalDate data) {
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    // Método para calcular o valor total do pedido
    public BigDecimal getValorTotal() {
        return preco.multiply(new BigDecimal(quantidade));
    }

    // Método para comparar se este pedido é mais barato que outro
    public boolean isMaisBaratoQue(Pedido outroPedido) {
        return this.getValorTotal().compareTo(outroPedido.getValorTotal()) < 0;
    }

    // Método para comparar se este pedido é mais caro que outro
    public boolean isMaisCaroQue(Pedido outroPedido) {
        return this.getValorTotal().compareTo(outroPedido.getValorTotal()) > 0;
    }

    // Método toString para exibir informações do pedido incluindo valor total
    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + cliente.getNome() +
                ", preço=" + preco +
                ", quantidade=" + quantidade +
                ", valor total=" + getValorTotal() +
                '}';
    }
}

