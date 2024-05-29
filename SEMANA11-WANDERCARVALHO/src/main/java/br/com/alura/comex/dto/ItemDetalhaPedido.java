package br.com.alura.comex.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ItemDetalhaPedido {

    private LocalDate data;
    private BigDecimal valor;
    private BigDecimal desconto;
    private List<ItemPedidoDTO> produtos;
    private ClienteResumoDTO cliente;

    public ItemDetalhaPedido(LocalDate data, BigDecimal valor, BigDecimal desconto, List<ItemPedidoDTO> produtos, ClienteResumoDTO cliente) {
        this.data = data;
        this.valor = valor;
        this.desconto = desconto;
        this.produtos = produtos;
        this.cliente = cliente;
    }

    // Getters e setters

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public List<ItemPedidoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ItemPedidoDTO> produtos) {
        this.produtos = produtos;
    }

    public ClienteResumoDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteResumoDTO cliente) {
        this.cliente = cliente;
    }
}


