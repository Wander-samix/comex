package br.com.alura.comex.testes;

import java.math.BigDecimal;
import java.util.Scanner;

public class TesteProduto {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Lendo o nome do produto
            System.out.print("Por favor, insira o nome do produto: ");
            String nome = scanner.nextLine();

            // Lendo a descrição do produto
            System.out.print("Por favor, insira a descrição do produto: ");
            String descricao = scanner.nextLine();

            // Lendo o preço unitário do produto
            BigDecimal precoUnitario = lerPrecoUnitario(scanner);
            scanner.nextLine(); // Consumindo a quebra de linha após o preço

            // Lendo a quantidade do produto
            int quantidade = lerQuantidade(scanner);
            scanner.nextLine(); // Consumindo a quebra de linha após a quantidade

            // Lendo a categoria do produto
            System.out.print("Por favor, insira a categoria do produto: ");
            String categoria = scanner.nextLine();

            // Aqui você poderia criar um objeto Produto e fazer algo com ele
            System.out.println("Produto criado: " + nome + ", " + descricao + ", " + precoUnitario + ", " + quantidade + ", " + categoria);

        } finally {
            scanner.close();
        }
    }

    private static BigDecimal lerPrecoUnitario(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Por favor, insira o preço unitário do produto: ");
                return scanner.nextBigDecimal();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número válido para o preço.");
                scanner.nextLine(); // Limpa o buffer do scanner completamente
            }
        }
    }

    private static int lerQuantidade(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Por favor, insira a quantidade do produto: ");
                return scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro válido para a quantidade.");
                scanner.nextLine(); // Limpa o buffer do scanner completamente
            }
        }
    }
}

