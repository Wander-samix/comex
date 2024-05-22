//package br.com.alura.comex.dao;
//
//import br.com.alura.comex.model.*;
//import br.com.alura.comex.testes.ConsultaCep;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.NoResultException;
//import javax.persistence.TypedQuery;
//import javax.persistence.Query;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Scanner;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import ch.qos.logback.classic.Level;
//import ch.qos.logback.classic.LoggerContext;
//import static javax.persistence.Persistence.createEntityManagerFactory;
//
//public class CadastrarProdutoPedido {
//
//    private static Scanner scanner;
//    private static final Logger logger = LoggerFactory.getLogger(CadastrarProdutoPedido.class);
//
//    public static void main(String[] args) {
//        EntityManagerFactory factory = createEntityManagerFactory("oracle");
//        EntityManager entityManager = factory.createEntityManager();
//        setScanner(new Scanner(System.in));
//
//        try {
//            setLoggingLevel(Level.ERROR); // Reduz o nível de log antes da interação com o usuário
//
//            System.out.println("Digite 'sair' a qualquer momento para terminar o programa.");
//
//            boolean running = true;
//            while (running) {
//                System.out.println("Selecione uma opção:");
//                System.out.println("1. Cadastrar nova compra");
//                System.out.println("2. Consultar compras de um cliente específico");
//                System.out.println("3. Consultar compras de todos os clientes");
//                System.out.println("4. Exibir o cliente mais fiel");
//                System.out.println("5. Sair");
//                String option = getRequiredInput("Opção:");
//
//                switch (option) {
//                    case "1":
//                        cadastrarNovaCompra(entityManager);
//                        break;
//                    case "2":
//                        consultarComprasCliente(entityManager);
//                        break;
//                    case "3":
//                        consultarComprasTodosClientes(entityManager);
//                        break;
//                    case "4":
//                        exibirClienteMaisFiel(entityManager);
//                        break;
//                    case "5":
//                        running = false;
//                        break;
//                    default:
//                        System.out.println("Opção inválida. Por favor, tente novamente.");
//                }
//            }
//        } catch (Exception e) {
//            logger.error("Ocorreu uma exceção: ", e);
//            if (entityManager.getTransaction().isActive()) {
//                entityManager.getTransaction().rollback();
//            }
//        } finally {
//            getScanner().close();
//            if (entityManager.isOpen()) {
//                entityManager.close();
//            }
//            if (factory.isOpen()) {
//                factory.close();
//            }
//            setLoggingLevel(Level.DEBUG); // Restaura o nível de log após a interação
//        }
//    }
//
//    private static void cadastrarNovaCompra(EntityManager entityManager) {
//        try {
//            listarClientesCadastrados(entityManager);
//            String cpf = getRequiredInput("Por favor, insira o CPF do cliente:");
//            Cliente cliente = buscarClientePorCpf(entityManager, cpf);
//            Categoria categoria = getCategoriaData(entityManager);
//            Produto produto = getProdutoData(categoria);
//            Pedido pedido = getPedidoData(entityManager, cliente, cpf);
//            if (pedido != null && produto != null) {
//                ItemPedido itemPedido = new ItemPedido(produto, 2, produto.getPrecoUnitario(), TipoDesconto.NENHUM, pedido);
//                entityManager.getTransaction().begin();
//                entityManager.persist(itemPedido);
//                pedido.adicionarItem(itemPedido);
//                entityManager.merge(pedido);
//                entityManager.getTransaction().commit();
//                logger.info("Item do pedido cadastrado com sucesso: {}", itemPedido);
//            }
//            gerarRelatorio(entityManager);
//        } catch (Exception e) {
//            logger.error("Ocorreu uma exceção: ", e);
//            if (entityManager.getTransaction().isActive()) {
//                entityManager.getTransaction().rollback();
//            }
//        }
//    }
//
//    private static void listarClientesCadastrados(EntityManager entityManager) {
//        List<Cliente> clientes = entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
//        System.out.println("Clientes já cadastrados:");
//        clientes.stream().forEach(cliente -> System.out.println(cliente.getCpf() + " - " + cliente.getNome()));
//        System.out.println("-------------------------------------------------");
//    }
//
//    private static Categoria getCategoriaData(EntityManager entityManager) {
//        String nomeCategoria = getRequiredInput("Por favor, insira o nome da categoria:");
//        Categoria categoria = new Categoria(nomeCategoria);
//        entityManager.getTransaction().begin();
//        entityManager.persist(categoria);
//        entityManager.getTransaction().commit();
//        return categoria;
//    }
//
//    private static Produto getProdutoData(Categoria categoria) {
//        String nomeProduto = getRequiredInput("Por favor, insira o nome do produto:");
//        String descricao = getRequiredInput("Por favor, insira a descrição do produto:");
//        BigDecimal precoUnitario = new BigDecimal(getRequiredInput("Por favor, insira o preço unitário do produto:"));
//        int quantidade = Integer.parseInt(getRequiredInput("Por favor, insira a quantidade do produto:"));
//        return new Produto(nomeProduto, descricao, precoUnitario, quantidade, categoria);
//    }
//
//    private static Pedido getPedidoData(EntityManager entityManager, Cliente cliente, String cpf) {
//        if (cliente == null) {
//            System.out.println("Cliente não encontrado. Iniciando cadastro...");
//            String cep = getRequiredInput("Por favor, insira o CEP para consulta:");
//            Endereco endereco = new ConsultaCep().consultaCep(cep);
//            if (endereco == null) {
//                logger.error("Não foi possível encontrar o endereço para o CEP fornecido.");
//                return null;
//            }
//            endereco.setNumero(getRequiredInput("Por favor, insira o número do imóvel:"));
//            endereco.setComplemento(getInput("Por favor, insira o complemento do imóvel (opcional):"));
//            cliente = getClienteData(endereco, cpf);
//            if (cliente != null) {
//                entityManager.getTransaction().begin();
//                entityManager.persist(endereco);
//                cliente.setEndereco(endereco);
//                entityManager.persist(cliente);
//                entityManager.getTransaction().commit();
//            } else {
//                logger.error("Falha ao criar dados do cliente. Cliente retornou nulo.");
//                return null;
//            }
//        }
//        LocalDate data = LocalDate.now();
//        TipoDesconto tipoDesconto = TipoDesconto.NENHUM;
//        return new Pedido(cliente, data, tipoDesconto);
//    }
//
//    private static Cliente buscarClientePorCpf(EntityManager entityManager, String cpf) {
//        try {
//            TypedQuery<Cliente> query = entityManager.createQuery("SELECT c FROM Cliente c WHERE c.cpf = :cpf", Cliente.class);
//            query.setParameter("cpf", cpf);
//            return query.getSingleResult();
//        } catch (NoResultException e) {
//            return null;
//        }
//    }
//
//    private static Cliente getClienteData(Endereco endereco, String cpf) {
//        String nomeCliente = getRequiredInput("Por favor, insira o nome do cliente:");
//        String email = getEmailInput();
//        String profissao = getRequiredInput("Por favor, insira a profissão do cliente:");
//        String telefone = getRequiredInput("Por favor, insira o telefone do cliente:");
//        return new Cliente(nomeCliente, cpf, email, profissao, telefone, endereco);
//    }
//
//    private static void consultarComprasCliente(EntityManager entityManager) {
//        String cpf = getRequiredInput("Por favor, insira o CPF do cliente:");
//        Cliente cliente = buscarClientePorCpf(entityManager, cpf);
//        if (cliente == null) {
//            System.out.println("Cliente não encontrado.");
//            return;
//        }
//        List<Pedido> pedidos = entityManager.createQuery("SELECT p FROM Pedido p WHERE p.cliente = :cliente", Pedido.class)
//                .setParameter("cliente", cliente)
//                .getResultList();
//
//        pedidos.stream().forEach(pedido -> {
//            System.out.println("-------------------------------------------------------------------------");
//            System.out.println("Histórico de compras do cliente " + cliente.getNome() + " (" + cliente.getCpf() + "):");
//            System.out.println("-------------------------------------------------------------------------");
//            System.out.printf("Pedido ID: %-5d | Data: %-10s | Total: %-10.2f\n", pedido.getId(), pedido.getData(), pedido.getValorTotal());
//            System.out.println("Itens do Pedido:");
//            System.out.println("Produto                    Quantidade     Valor      Montante Vendido ");
//            pedido.getItensPedidos().stream().forEach(item -> System.out.printf("%-30s %-10d %-15.2f %-15.2f\n",
//                    item.getNomeProduto(), item.getQuantidade(), item.getPrecoUnitario(), item.calcularValorTotal()));
//        });
//        System.out.println("-------------------------------------------------------------------------");
//    }
//
//    private static void consultarComprasTodosClientes(EntityManager entityManager) {
//        List<Cliente> clientes = entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
//        System.out.println("-------------------------------------------------------------------------");
//        System.out.println("Relatório de compras de todos os clientes:");
//        clientes.stream().forEach(cliente -> {
//            System.out.println("-------------------------------------------------------------------------");
//            System.out.println("Cliente: " + cliente.getNome());
//            List<Pedido> pedidos = entityManager.createQuery("SELECT p FROM Pedido p WHERE p.cliente = :cliente", Pedido.class)
//                    .setParameter("cliente", cliente)
//                    .getResultList();
//            pedidos.stream().forEach(pedido -> {
//                System.out.println("Pedido ID: " + pedido.getId() + " | Data: " + pedido.getData() + " | Total: " + pedido.getValorTotal());
//                System.out.println("Itens do Pedido:");
//                System.out.println("Produto                    Quantidade     Valor      Montante Vendido ");
//                pedido.getItensPedidos().stream().forEach(item -> System.out.printf("%-30s %-10d %-15.2f %-15.2f\n",
//                        item.getNomeProduto(), item.getQuantidade(), item.getPrecoUnitario(), item.calcularValorTotal()));
//            });
//            System.out.println("-------------------------------------------------------------------------");
//        });
//    }
//
//    private static void exibirClienteMaisFiel(EntityManager entityManager) {
//        try {
//            Query query = entityManager.createQuery(
//                    "SELECT c.id, COUNT(p.id) AS totalPedidos " +
//                            "FROM Pedido p " +
//                            "JOIN p.cliente c " +
//                            "GROUP BY c.id " +
//                            "ORDER BY totalPedidos DESC"
//            );
//            query.setMaxResults(1);
//            Object[] resultado = (Object[]) query.getSingleResult();
//            Long clienteId = (Long) resultado[0];
//            Long totalPedidos = (Long) resultado[1];
//            Cliente cliente = entityManager.find(Cliente.class, clienteId);
//            System.out.println("-------------------------------------------------------------------------");
//            System.out.println("Cliente mais fiel:");
//            System.out.printf("%-30s %-20s\n", "Cliente", "Total de Pedidos");
//            System.out.printf("%-30s %-20d\n", cliente.getNome(), totalPedidos);
//            System.out.println("-------------------------------------------------------------------------");
//        } catch (NoResultException e) {
//            System.out.println("Nenhum cliente encontrado.");
//        }
//    }
//
//    private static void gerarRelatorio(EntityManager entityManager) {
//        List<Object[]> resultados = entityManager.createQuery(
//                        "SELECT c.nome, COUNT(ip), SUM(ip.quantidade * ip.precoUnitario) " +
//                                "FROM ItemPedido ip " +
//                                "JOIN ip.produto p " +
//                                "JOIN p.categoria c " +
//                                "GROUP BY c.nome", Object[].class)
//                .getResultList();
//        System.out.println("-------------------------------------------------------------------------");
//        System.out.println("Categoria                      Produtos Vendidos    Montante Vendido ");
//        resultados.stream().forEach(resultado -> {
//            String nomeCategoria = (String) resultado[0];
//            Long produtosVendidos = (Long) resultado[1];
//            BigDecimal montanteVendido = (BigDecimal) resultado[2];
//            System.out.printf("%-30s %-20s %-20s\n", nomeCategoria, produtosVendidos, montanteVendido);
//        });
//        System.out.println("-------------------------------------------------------------------------");
//    }
//
//    private static String getRequiredInput(String prompt) {
//        System.out.println(prompt);
//        String input = getScanner().nextLine();
//        if ("sair".equalsIgnoreCase(input)) {
//            System.out.println("Programa encerrado.");
//            System.exit(0);
//        }
//        if (input.trim().isEmpty()) {
//            System.out.println("Este campo é obrigatório. Por favor, forneça um valor.");
//            return getRequiredInput(prompt);
//        }
//        return input;
//    }
//
//    private static String getInput(String prompt) {
//        System.out.println(prompt);
//        return getScanner().nextLine();
//    }
//
//    private static String getEmailInput() {
//        String email = getRequiredInput("Por favor, insira o email do cliente:");
//        if (!isValidEmail(email)) {
//            System.out.println("Email inválido. Por favor, insira um email válido.");
//            return getEmailInput();
//        }
//        return email;
//    }
//
//    private static boolean isValidEmail(String email) {
//        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
//    }
//
//    private static Scanner getScanner() {
//        return scanner;
//    }
//
//    private static void setScanner(Scanner scanner) {
//        CadastrarProdutoPedido.scanner = scanner;
//    }
//
//    private static void setLoggingLevel(Level level) {
//        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
//        loggerContext.getLogger("org.hibernate").setLevel(level);
//        loggerContext.getLogger("org.springframework").setLevel(level);
//    }
//}

package br.com.alura.comex.dao;

import br.com.alura.comex.model.*;
import br.com.alura.comex.testes.ConsultaCep;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import static javax.persistence.Persistence.createEntityManagerFactory;

public class CadastrarProdutoPedido {

    private static Scanner scanner;
    private static final Logger logger = LoggerFactory.getLogger(CadastrarProdutoPedido.class);

    public static void main(String[] args) {
        EntityManagerFactory factory = createEntityManagerFactory("oracle");
        EntityManager entityManager = factory.createEntityManager();
        setScanner(new Scanner(System.in));

        try {
            setLoggingLevel(Level.ERROR); // Reduz o nível de log antes da interação com o usuário

            System.out.println("Digite 'sair' a qualquer momento para terminar o programa.");

            boolean running = true;
            while (running) {
                System.out.println("Selecione uma opção:");
                System.out.println("1. Cadastrar nova compra");
                System.out.println("2. Consultar compras de um cliente específico");
                System.out.println("3. Consultar compras de todos os clientes");
                System.out.println("4. Exibir o cliente mais fiel");
                System.out.println("5. Relatório de total de clientes ativos e total vendido no mês");
                System.out.println("6. Sair");
                String option = getRequiredInput("Opção:");

                switch (option) {
                    case "1":
                        cadastrarNovaCompra(entityManager);
                        break;
                    case "2":
                        consultarComprasCliente(entityManager);
                        break;
                    case "3":
                        consultarComprasTodosClientes(entityManager);
                        break;
                    case "4":
                        exibirClienteMaisFiel(entityManager);
                        break;
                    case "5":
                        gerarRelatorioClientesAtivosEVendas(entityManager);
                        break;
                    case "6":
                        running = false;
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, tente novamente.");
                }
            }
        } catch (Exception e) {
            logger.error("Ocorreu uma exceção: ", e);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            getScanner().close();
            if (entityManager.isOpen()) {
                entityManager.close();
            }
            if (factory.isOpen()) {
                factory.close();
            }
            setLoggingLevel(Level.DEBUG); // Restaura o nível de log após a interação
        }
    }

    private static void cadastrarNovaCompra(EntityManager entityManager) {
        try {
            listarClientesCadastrados(entityManager);
            String cpf = getRequiredInput("Por favor, insira o CPF do cliente:");
            Cliente cliente = buscarClientePorCpf(entityManager, cpf);
            Categoria categoria = getCategoriaData(entityManager);
            Produto produto = getProdutoData(categoria);

            // Persistir o produto antes de criar o ItemPedido
            entityManager.getTransaction().begin();
            entityManager.persist(produto);
            entityManager.getTransaction().commit();

            Pedido pedido = getPedidoData(entityManager, cliente, cpf);

            // Persistir o pedido antes de criar o ItemPedido
            entityManager.getTransaction().begin();
            entityManager.persist(pedido);
            entityManager.getTransaction().commit();

            if (pedido != null && produto != null) {
                int quantidadeItens = Integer.parseInt(getRequiredInput("Por favor, insira a quantidade de itens para o pedido:"));
                ItemPedido itemPedido = new ItemPedido(produto, quantidadeItens, produto.getPrecoUnitario(), TipoDesconto.NENHUM, pedido);
                entityManager.getTransaction().begin();
                entityManager.persist(itemPedido);
                pedido.adicionarItem(itemPedido);
                entityManager.merge(pedido);
                entityManager.getTransaction().commit();
                logger.info("Item do pedido cadastrado com sucesso: {}", itemPedido);
            }
            gerarRelatorio(entityManager);
        } catch (Exception e) {
            logger.error("Ocorreu uma exceção: ", e);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    private static void listarClientesCadastrados(EntityManager entityManager) {
        List<Cliente> clientes = entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        System.out.println("-------------------------------------------------");
        System.out.println("              RELATÓRIO DE CLIENTES              ");
        System.out.println("-------------------------------------------------");
        System.out.printf("%-5s %-20s %-15s %-30s %-20s %-15s\n", "ID", "Nome", "CPF", "Email", "Profissão", "Telefone");
        System.out.println("-------------------------------------------------");
        clientes.forEach(cliente -> {
            System.out.printf("%-5d %-20s %-15s %-30s %-20s %-15s\n",
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getEmail(),
                    cliente.getProfissao(),
                    cliente.getTelefone());
        });
        System.out.println("-------------------------------------------------");
    }

    private static Categoria getCategoriaData(EntityManager entityManager) {
        String nomeCategoria = getRequiredInput("Por favor, insira o nome da categoria:");
        Categoria categoria = new Categoria(nomeCategoria);
        entityManager.getTransaction().begin();
        entityManager.persist(categoria);
        entityManager.getTransaction().commit();
        return categoria;
    }

    private static Produto getProdutoData(Categoria categoria) {
        String nomeProduto = getRequiredInput("Por favor, insira o nome do produto:");
        String descricao = getRequiredInput("Por favor, insira a descrição do produto:");
        BigDecimal precoUnitario = new BigDecimal(getRequiredInput("Por favor, insira o preço unitário do produto:"));
        int quantidade = Integer.parseInt(getRequiredInput("Por favor, insira a quantidade do produto:"));
        return new Produto(nomeProduto, descricao, precoUnitario, quantidade, categoria);
    }

    private static Pedido getPedidoData(EntityManager entityManager, Cliente cliente, String cpf) {
        if (cliente == null) {
            System.out.println("Cliente não encontrado. Iniciando cadastro...");
            String cep = getRequiredInput("Por favor, insira o CEP para consulta:");
            Endereco endereco = new ConsultaCep().consultaCep(cep);
            if (endereco == null) {
                logger.error("Não foi possível encontrar o endereço para o CEP fornecido.");
                return null;
            }
            endereco.setNumero(getRequiredInput("Por favor, insira o número do imóvel:"));
            endereco.setComplemento(getInput("Por favor, insira o complemento do imóvel (opcional):"));
            cliente = getClienteData(endereco, cpf);
            if (cliente != null) {
                entityManager.getTransaction().begin();
                entityManager.persist(endereco);
                cliente.setEndereco(endereco);
                entityManager.persist(cliente);
                entityManager.getTransaction().commit();
            } else {
                logger.error("Falha ao criar dados do cliente. Cliente retornou nulo.");
                return null;
            }
        }
        LocalDate data = LocalDate.now();
        TipoDesconto tipoDesconto = TipoDesconto.NENHUM;
        return new Pedido(cliente, data, tipoDesconto);
    }

    private static Cliente buscarClientePorCpf(EntityManager entityManager, String cpf) {
        try {
            TypedQuery<Cliente> query = entityManager.createQuery("SELECT c FROM Cliente c WHERE c.cpf = :cpf", Cliente.class);
            query.setParameter("cpf", cpf);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private static Cliente getClienteData(Endereco endereco, String cpf) {
        String nomeCliente = getRequiredInput("Por favor, insira o nome do cliente:");
        String email = getEmailInput();
        String profissao = getRequiredInput("Por favor, insira a profissão do cliente:");
        String telefone = getRequiredInput("Por favor, insira o telefone do cliente:");
        return new Cliente(nomeCliente, cpf, email, profissao, telefone, endereco);
    }

    private static void consultarComprasCliente(EntityManager entityManager) {
        String cpf = getRequiredInput("Por favor, insira o CPF do cliente:");
        Cliente cliente = buscarClientePorCpf(entityManager, cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        exibirDetalhesCliente(cliente);
        exibirPedidosCliente(entityManager, cliente);
    }

    private static void consultarComprasTodosClientes(EntityManager entityManager) {
        List<Cliente> clientes = entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        System.out.println("-------------------------------------------------");
        System.out.println("       RELATÓRIO DE COMPRAS DE TODOS OS CLIENTES       ");
        System.out.println("-------------------------------------------------");

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
        } else {
            clientes.forEach(cliente -> {
                exibirDetalhesCliente(cliente);
                exibirPedidosCliente(entityManager, cliente);
                System.out.println("-------------------------------------------------");
            });
        }
    }
    private static void exibirClienteMaisFiel(EntityManager entityManager) {
        try {
            Query query = entityManager.createQuery(
                    "SELECT c.id, COUNT(p.id) AS totalPedidos " +
                            "FROM Pedido p " +
                            "JOIN p.cliente c " +
                            "GROUP BY c.id " +
                            "ORDER BY totalPedidos DESC"
            );
            query.setMaxResults(1);
            Object[] resultado = (Object[]) query.getSingleResult();
            Long clienteId = (Long) resultado[0];
            Long totalPedidos = (Long) resultado[1];
            Cliente cliente = entityManager.find(Cliente.class, clienteId);
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("Cliente mais fiel:");
            System.out.printf("%-30s %-20s\n", "Cliente", "Total de Pedidos");
            System.out.printf("%-30s %-20d\n", cliente.getNome(), totalPedidos);
            System.out.println("-------------------------------------------------------------------------");
        } catch (NoResultException e) {
            System.out.println("Nenhum cliente encontrado.");
        }
    }

    private static void gerarRelatorio(EntityManager entityManager) {
        List<Cliente> clientes = entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        clientes.forEach(cliente -> {
            exibirDetalhesCliente(cliente);
            exibirPedidosCliente(entityManager, cliente);
        });
    }

    private static void gerarRelatorioClientesAtivosEVendas(EntityManager entityManager) {
        YearMonth currentMonth = YearMonth.now();
        LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();

        // Total de clientes ativos (clientes com pelo menos um pedido no mês)
        TypedQuery<Long> queryClientesAtivos = entityManager.createQuery(
                "SELECT COUNT(DISTINCT p.cliente.id) FROM Pedido p WHERE p.data BETWEEN :startDate AND :endDate",
                Long.class);
        queryClientesAtivos.setParameter("startDate", startDate);
        queryClientesAtivos.setParameter("endDate", endDate);
        Long totalClientesAtivos = queryClientesAtivos.getSingleResult();

        // Total vendido no mês
        TypedQuery<BigDecimal> queryTotalVendas = entityManager.createQuery(
                "SELECT SUM(ip.precoUnitario * ip.quantidade) FROM ItemPedido ip WHERE ip.pedido.data BETWEEN :startDate AND :endDate",
                BigDecimal.class);
        queryTotalVendas.setParameter("startDate", startDate);
        queryTotalVendas.setParameter("endDate", endDate);
        BigDecimal totalVendas = queryTotalVendas.getSingleResult();

        System.out.println("-------------------------------------------------");
        System.out.println(" RELATÓRIO DE CLIENTES ATIVOS E TOTAL VENDIDO NO MÊS ");
        System.out.println("-------------------------------------------------");
        System.out.printf("Total de clientes ativos no mês: %d\n", totalClientesAtivos);
        System.out.printf("Total vendido no mês: R$ %.2f\n", totalVendas);
        System.out.println("-------------------------------------------------");
    }

    private static void exibirDetalhesCliente(Cliente cliente) {
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Cliente #" + cliente.getId() + ":");
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("Email: " + cliente.getEmail());
        System.out.println("Profissão: " + cliente.getProfissao());
        System.out.println("Telefone: " + cliente.getTelefone());
        Endereco endereco = cliente.getEndereco();
        if (endereco != null) {
            System.out.println("Endereço: " + endereco.getLogradouro() + ", Número: " + endereco.getNumero() + ", Complemento: " + endereco.getComplemento() + ", CEP: " + endereco.getCep());
        }
    }

    private static void exibirPedidosCliente(EntityManager entityManager, Cliente cliente) {
        List<Pedido> pedidos = entityManager.createQuery("SELECT p FROM Pedido p WHERE p.cliente = :cliente", Pedido.class)
                .setParameter("cliente", cliente)
                .getResultList();

        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado para este cliente.");
        } else {
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("Pedidos do Cliente:");
            pedidos.forEach(pedido -> {
                System.out.println("Pedido ID: " + pedido.getId() + " | Data: " + pedido.getData() + " | Total: " + pedido.getValorTotal());
                System.out.println("Itens do Pedido:");
                System.out.printf("%-30s %-10s %-15s %-15s\n", "Produto", "Quantidade", "Preço Unitário", "Valor Total");
                pedido.getItensPedidos().forEach(item -> System.out.printf("%-30s %-10d %-15.2f %-15.2f\n",
                        item.getNomeProduto(), item.getQuantidade(), item.getPrecoUnitario(), item.calcularValorTotal()));
                System.out.println("-------------------------------------------------------------------------");
            });
            System.out.println("-------------------------------------------------------------------------");
        }
    }

    private static String getRequiredInput(String prompt) {
        System.out.println(prompt);
        String input = getScanner().nextLine();
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
        return getScanner().nextLine();
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
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    private static Scanner getScanner() {
        return scanner;
    }

    private static void setScanner(Scanner scanner) {
        CadastrarProdutoPedido.scanner = scanner;
    }

    private static void setLoggingLevel(Level level) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLogger("org.hibernate").setLevel(level);
        loggerContext.getLogger("org.springframework").setLevel(level);
    }
}
