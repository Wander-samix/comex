package br.com.alura.comex.testes;

import br.com.alura.comex.model.Produto;

public class ImpressaoDadosProduto {
    public static void main(String[] args) {
        // Criação e inicialização do produto
        Produto produto = new Produto("Livro", "Conjunto de folhas impressas.", 19.99, 100);

        // Invocação do método de impressão dos detalhes do produto
        produto.imprimirDetalhes();
    }
}
