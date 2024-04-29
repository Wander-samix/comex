package br.com.alura.comex.DAO;

import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.Endereco;
import br.com.alura.comex.testes.ConsultaCep;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;

public class CadastraCliente {

    private static Scanner scanner;
    private static final Logger logger = LoggerFactory.getLogger(CadastraCliente.class);

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle");
        EntityManager entityManager = factory.createEntityManager();
        scanner = new Scanner(System.in);

        try {
            setLoggingLevel(Level.ERROR); // Reduz o nível de log antes da interação com o usuário

            System.out.println("Digite 'sair' a qualquer momento para terminar o programa.");
            String cep = getRequiredInput("Por favor, insira o CEP para consulta:");
            Endereco endereco = new ConsultaCep().consultaCep(cep);

            if (endereco == null) {
                logger.error("Não foi possível encontrar o endereço para o CEP fornecido.");
                return;
            }

            Cliente cliente = getClienteData(endereco);
            if (cliente != null) {
                logger.info("Informações do cliente antes da persistência: {}", cliente);
                logger.info("Informações do endereço antes da persistência: {}", endereco);

                EntityManager em = factory.createEntityManager();
                entityManager.getTransaction().begin(); // Inicia a transação
                cliente.salvar(em); // Persiste o cliente e seu endereço
                entityManager.getTransaction().commit(); // Faz o commit da transação
                entityManager.close(); // Fecha o EntityManager após o uso

                logger.info("Cliente cadastrado com sucesso: {}", cliente);
            } else {
                logger.error("Falha ao criar dados do cliente. Cliente retornou nulo.");
            }
        } finally {
            scanner.close();
            factory.close();
            setLoggingLevel(Level.DEBUG); // Restaura o nível de log após a interação
        }
    }

    private static Cliente getClienteData(Endereco endereco) {
        String nomeCliente = getRequiredInput("Por favor, insira o nome do cliente:");
        if (nomeCliente.isEmpty()) {
            logger.error("O nome do cliente é obrigatório e não foi fornecido.");
            return null; // Retorna nulo para evitar tentativa de persistência com nome nulo.
        }
        String cpf = getRequiredInput("Por favor, insira o CPF do cliente:");
        String email = getEmailInput();
        String profissao = getRequiredInput("Por favor, insira a profissão do cliente:");
        String telefone = getRequiredInput("Por favor, insira o telefone do cliente:");
        endereco.setNumero(getRequiredInput("Por favor, insira o número do imóvel:"));
        endereco.setComplemento(getInput("Por favor, insira o complemento do imóvel (opcional):"));

        return new Cliente(nomeCliente, cpf, email, profissao, telefone, endereco);
    }

    private static String getRequiredInput(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();
        if ("sair".equalsIgnoreCase(input)) {
            System.out.println("Programa encerrado.");
            System.exit(0);
        }
        if (input.trim().isEmpty()) {
            System.out.println("Este campo é obrigatório. Por favor, forneça um valor.");
            return getRequiredInput(prompt);
        }
        return input;
    }

    private static String getInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    private static String getEmailInput() {
        String email = getRequiredInput("Por favor, insira o email do cliente:");
        if (!isValidEmail(email)) {
            System.out.println("Email inválido. Por favor, insira um email válido.");
            return getEmailInput();
        }
        return email;
    }

    private static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(\\[?)(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}(\\]?)$");
    }

    private static void setLoggingLevel(Level newLevel) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(newLevel);
    }
}
