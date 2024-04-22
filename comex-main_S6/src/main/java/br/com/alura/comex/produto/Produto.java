package br.com.alura.comex.produto;

import java.util.Objects;

public class Produto {
    private final String nome;
    private final String descricao;
    private final double precoUnitario;
    private final int quantidade;

    // Construtor que valida a obrigatoriedade do nome
    public Produto(String nome, String descricao, double precoUnitario, int quantidade) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório e não pode ser vazio.");
        }
        this.nome = nome;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // Método toString
    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preço unitário=" + precoUnitario +
                ", quantidade=" + quantidade +
                '}';
    }

    // Método equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Produto produto = (Produto) obj;
        return Double.compare(produto.precoUnitario, precoUnitario) == 0 &&
                quantidade == produto.quantidade &&
                nome.equals(produto.nome) &&
                descricao.equals(produto.descricao);
    }

    // Método hashCode
    @Override
    public int hashCode() {
        return Objects.hash(nome, descricao, precoUnitario, quantidade);
    }

    // Método para imprimir os detalhes do produto
    public void imprimirDetalhes() {
        System.out.println(">> Dados do produto");
        System.out.println(":: Nome: " + nome);
        System.out.println(":: Descrição: " + descricao);
        // Adicionalmente, você pode imprimir preço e quantidade se necessário
        System.out.println(":: Preço Unitário: " + precoUnitario);
        System.out.println(":: Quantidade: " + quantidade);
    }
}
