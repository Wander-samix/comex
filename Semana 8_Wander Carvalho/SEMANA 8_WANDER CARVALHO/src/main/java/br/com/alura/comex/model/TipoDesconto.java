package br.com.alura.comex.model;

import java.math.BigDecimal;

public enum TipoDesconto {
    FIDELIDADE("Desconto de Fidelidade", 0.10),  // 10% de desconto
    PROMOCIONAL("Desconto Promocional", 0.15),  // 15% de desconto
    NENHUM("Sem Desconto", 0.00);               // Sem desconto

    private final String descricao;
    private final double percentual;

    TipoDesconto(String descricao, double percentual) {
        this.descricao = descricao;
        this.percentual = percentual;
    }

    public double getPercentual() {
        return percentual;
    }

    @Override
    public String toString() {
        return this.descricao + " (" + (percentual * 100) + "%)";
    }
}
