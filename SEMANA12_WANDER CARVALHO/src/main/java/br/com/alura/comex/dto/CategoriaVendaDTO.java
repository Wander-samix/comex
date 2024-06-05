package br.com.alura.comex.dto;

import java.math.BigDecimal;

public class CategoriaVendaDTO {
    private String nomeCategoria;
    private Long quantidadeVendida;
    private BigDecimal montanteVendido;

    public CategoriaVendaDTO(String nomeCategoria, Long quantidadeVendida, BigDecimal montanteVendido) {
        this.nomeCategoria = nomeCategoria;
        this.quantidadeVendida = quantidadeVendida;
        this.montanteVendido = montanteVendido;
    }

    // Getters e setters

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public Long getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(Long quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }

    public BigDecimal getMontanteVendido() {
        return montanteVendido;
    }

    public void setMontanteVendido(BigDecimal montanteVendido) {
        this.montanteVendido = montanteVendido;
    }
}
