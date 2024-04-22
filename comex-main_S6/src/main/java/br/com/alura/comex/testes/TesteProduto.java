package br.com.alura.comex.testes;

import br.com.alura.comex.produto.Produto;
import java.util.Scanner;

public class TesteProduto {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Por favor, insira o nome do produto:");
        String nome = scanner.nextLine().trim();

        // Verificar se o nome é válido (não vazio e não nulo)
        while (nome.isEmpty()) {
            System.out.println("O nome do produto é obrigatório e não pode ser vazio. Por favor, insira o nome do produto:");
            nome = scanner.nextLine().trim();
        }

        System.out.println("Por favor, insira a descrição do produto:");
        String descricao = scanner.nextLine().trim();

        System.out.println("Por favor, insira o preço unitário do produto:");
        double precoUnitario = scanner.nextDouble();

        System.out.println("Por favor, insira a quantidade do produto:");
        int quantidade = scanner.nextInt();

        // Criação do objeto Produto com dados fornecidos pelo usuário
        Produto produto = new Produto(nome, descricao, precoUnitario, quantidade);

        // Impressão das informações do produto
        System.out.println("Informações do Produto:");
        System.out.println(produto);

        scanner.close(); // Fechar o scanner após o uso
    }
}
