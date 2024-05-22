package br.com.alura.comex.model;

public class RelatorioCliente {
    private String nomeCliente;
    private int quantidadePedidos;
    private double montanteGasto;

    public RelatorioCliente(String nomeCliente, int quantidadePedidos, double montanteGasto) {
        this.nomeCliente = nomeCliente;
        this.quantidadePedidos = quantidadePedidos;
        this.montanteGasto = montanteGasto;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public int getQuantidadePedidos() {
        return quantidadePedidos;
    }

    public double getMontanteGasto() {
        return montanteGasto;
    }
}
