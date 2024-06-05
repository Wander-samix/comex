package br.com.alura.comex.dto;

import br.com.alura.comex.model.Produto;

import java.math.BigDecimal;

public class ProdutoDTO {

    private String nome;
    private BigDecimal preco;
    private String descricao;
    private Integer quantidadeEmEstoque;
    private Long categoriaId;
    private String categoriaNome;

    public ProdutoDTO(Produto produto) {
        this.nome = produto.getNome();
        this.preco = produto.getPrecoUnitario();
        this.descricao = produto.getDescricao();
        this.quantidadeEmEstoque = produto.getQuantidade();
        this.categoriaId = produto.getCategoria().getId();
        this.categoriaNome = produto.getCategoria().getNome();
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }
}
