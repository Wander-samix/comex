package br.com.alura.comex.testes;

import br.com.alura.comex.cliente.Cliente;
import br.com.alura.comex.endereco.Endereco;
import br.com.alura.comex.endereco.ConsultaCep;
import java.util.Scanner;

public class TesteCliente {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsultaCep cepService = new ConsultaCep(); // Supondo que essa classe esteja corretamente implementada

        System.out.println("Digite 'sair' a qualquer momento para terminar o programa.");

        // Solicita o CEP do usuário
        System.out.println("Por favor, insira o CEP para consulta:");
        String cep = scanner.nextLine();
        if ("sair".equalsIgnoreCase(cep)) {
            System.out.println("Programa encerrado.");
            scanner.close();
            return;
        }

        // Consulta o CEP usando o serviço ViaCep
        Endereco endereco = cepService.consultaCep(cep);
        if (endereco == null) {
            System.out.println("Não foi possível encontrar o endereço para o CEP fornecido.");
            scanner.close();
            return;
        }

        System.out.println("Endereço encontrado:");
        System.out.println("Logradouro: " + endereco.getLogradouro());

        // Solicitar número do endereço
        System.out.println("Por favor, insira o número do endereço:");
        String numero = scanner.nextLine();
        if ("sair".equalsIgnoreCase(numero)) {
            System.out.println("Programa encerrado.");
            scanner.close();
            return;
        }
        endereco.setNumero(numero);

        // Solicitar complemento do endereço
        System.out.println("Por favor, insira o complemento do endereço (se houver):");
        String complemento = scanner.nextLine();
        if ("sair".equalsIgnoreCase(complemento)) {
            System.out.println("Programa encerrado.");
            scanner.close();
            return;
        }
        endereco.setComplemento(complemento);

        // Solicitação dos dados do Cliente
        System.out.println("Por favor, insira o nome do cliente:");
        String nomeCliente = scanner.nextLine();
        if ("sair".equalsIgnoreCase(nomeCliente)) {
            System.out.println("Programa encerrado.");
            scanner.close();
            return;
        }

        System.out.println("Por favor, insira o CPF do cliente:");
        String cpf = scanner.nextLine();
        if ("sair".equalsIgnoreCase(cpf)) {
            System.out.println("Programa encerrado.");
            scanner.close();
            return;
        }

        System.out.println("Por favor, insira o email do cliente:");
        String email = scanner.nextLine();
        if ("sair".equalsIgnoreCase(email)) {
            System.out.println("Programa encerrado.");
            scanner.close();
            return;
        }

        System.out.println("Por favor, insira a profissão do cliente:");
        String profissao = scanner.nextLine();
        if ("sair".equalsIgnoreCase(profissao)) {
            System.out.println("Programa encerrado.");
            scanner.close();
            return;
        }

        System.out.println("Por favor, insira o telefone do cliente:");
        String telefone = scanner.nextLine();
        if ("sair".equalsIgnoreCase(telefone)) {
            System.out.println("Programa encerrado.");
            scanner.close();
            return;
        }

        // Criação de um objeto Cliente usando o objeto Endereco criado e os dados coletados
        Cliente cliente = new Cliente(nomeCliente, cpf, email, profissao, telefone, endereco);

        // Impressão das informações do Cliente e do Endereço completo no console
        System.out.println("Informações completas do Cliente e Endereço:");
        System.out.println(cliente);

        scanner.close();
    }
}
