package br.com.alura.comex.service;

import br.com.alura.comex.model.*;
import br.com.alura.comex.repository.*;
import br.com.alura.comex.testes.ConsultaCep;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import jakarta.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Scanner;

@Service
public class CadastrarProdutoPedido implements CommandLineRunner {

    private static Scanner scanner;
    private static final Logger logger = LoggerFactory.getLogger(CadastrarProdutoPedido.class);

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public void run(String... args) {
        setScanner(new Scanner(System.in));
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
                    cadastrarNovaCompra();
                    break;
                case "2":
                    consultarComprasCliente();
                    break;
                case "3":
                    consultarComprasTodosClientes();
                    break;
                case "4":
                    exibirClienteMaisFiel();
                    break;
                case "5":
                    gerarRelatorioClientesAtivosEVendas();
                    break;
                case "6":
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
            }
        }
        getScanner().close();
        setLoggingLevel(Level.DEBUG); // Restaura o nível de log após a interação
    }

    private void cadastrarNovaCompra() {
        try {
            listarClientesCadastrados();
            String cpf = getRequiredInput("Por favor, insira o CPF do cliente:");
            Cliente cliente = clienteRepository.findByCpf(cpf);
            if (cliente == null) {
                System.out.println("Cliente não encontrado. Iniciando cadastro...");
                String cep = getRequiredInput("Por favor, insira o CEP para consulta:");
                Endereco endereco = new ConsultaCep().consultaCep(cep);
                if (endereco == null) {
                    logger.error("Não foi possível encontrar o endereço para o CEP fornecido.");
                    return;
                }
                endereco.setNumero(getRequiredInput("Por favor, insira o número do imóvel:"));
                endereco.setComplemento(getInput("Por favor, insira o complemento do imóvel (opcional):"));

                // Salva o endereço antes de criar o cliente
                endereco = enderecoRepository.save(endereco);

                cliente = getClienteData(endereco, cpf);
                if (cliente != null) {
                    cliente.setEndereco(endereco);
                    clienteRepository.save(cliente);
                } else {
                    logger.error("Falha ao criar dados do cliente. Cliente retornou nulo.");
                    return;
                }
            }

            Categoria categoria = getCategoriaData();
            Produto produto = getProdutoData(categoria);
            produtoRepository.save(produto);

            // Certifique-se de que tipoDesconto seja inicializado corretamente
            TipoDesconto tipoDesconto = TipoDesconto.NENHUM;
            Pedido pedido = new Pedido(cliente, LocalDate.now(), tipoDesconto);
            pedidoRepository.save(pedido); // Salva o pedido com o cliente atribuído

            int quantidadeItens = Integer.parseInt(getRequiredInput("Por favor, insira a quantidade de itens:"));
            BigDecimal precoUnitario = produto.getPrecoUnitario();

            // Verifique se o precoUnitario está definido corretamente
            if (precoUnitario == null) {
                logger.error("O preço unitário do produto está nulo.");
                return;
            }

            // Log para verificar os valores antes de criar o itemPedido
            logger.info("Criando itemPedido: produto={}, quantidade={}, precoUnitario={}", produto.getNome(), quantidadeItens, precoUnitario);

            ItemPedido itemPedido = new ItemPedido(produto, quantidadeItens, precoUnitario, TipoDesconto.NENHUM, pedido);

            // Garantir que o preço unitário está sendo definido
            itemPedido.setPrecoUnitario(precoUnitario);

            // Log para verificar os valores antes de salvar
            logger.info("Salvando itemPedido: produto={}, quantidade={}, precoUnitario={}", produto.getNome(), quantidadeItens, precoUnitario);

            itemPedidoRepository.save(itemPedido);
            pedido.adicionarItem(itemPedido); // Adiciona o item ao pedido
            pedidoRepository.save(pedido); // Atualiza o pedido com o item adicionado

            logger.info("Item do pedido cadastrado com sucesso: {}", itemPedido);
            gerarRelatorio();
        } catch (Exception e) {
            logger.error("Ocorreu uma exceção: ", e);
        }
    }


    private void listarClientesCadastrados() {
        List<Cliente> clientes = clienteRepository.findAll();
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

    private Categoria getCategoriaData() {
        String nomeCategoria = getRequiredInput("Por favor, insira o nome da categoria:");
        Categoria categoria = new Categoria(nomeCategoria);
        categoriaRepository.save(categoria);
        return categoria;
    }

    private Produto getProdutoData(Categoria categoria) {
        String nomeProduto = getRequiredInput("Por favor, insira o nome do produto:");
        String descricao = getRequiredInput("Por favor, insira a descrição do produto:");
        BigDecimal precoUnitario = new BigDecimal(getRequiredInput("Por favor, insira o preço unitário do produto:"));
        int quantidade = Integer.parseInt(getRequiredInput("Por favor, insira a quantidade do produto:"));
        return new Produto(nomeProduto, descricao, precoUnitario, quantidade, categoria);
    }

    private Cliente getClienteData(Endereco endereco, String cpf) {
        String nomeCliente = getRequiredInput("Por favor, insira o nome do cliente:");
        String email = getEmailInput();
        String profissao = getRequiredInput("Por favor, insira a profissão do cliente:");
        String telefone = getRequiredInput("Por favor, insira o telefone do cliente:");
        return new Cliente(nomeCliente, cpf, email, profissao, telefone, endereco, true, LocalDate.now());
    }

    private void consultarComprasCliente() {
        String cpf = getRequiredInput("Por favor, insira o CPF do cliente:");
        Cliente cliente = clienteRepository.findByCpfWithPedidos(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        exibirDetalhesCliente(cliente);
        exibirPedidosCliente(cliente);
    }

    private void consultarComprasTodosClientes() {
        List<Cliente> clientes = clienteRepository.findAllWithPedidos();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
        } else {
            clientes.forEach(cliente -> {
                exibirDetalhesCliente(cliente);
                exibirPedidosCliente(cliente);
                System.out.println("-------------------------------------------------");
            });
        }
    }

    private void exibirClienteMaisFiel() {
        try {
            List<Object[]> resultado = clienteRepository.findClienteMaisFiel(PageRequest.of(0, 1));
            if (!resultado.isEmpty()) {
                Object[] clienteInfo = resultado.get(0);
                Long clienteId = (Long) clienteInfo[0];
                Long totalPedidos = (Long) clienteInfo[1];
                Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Cliente mais fiel:");
                System.out.printf("%-30s %-20s\n", "Cliente", "Total de Pedidos");
                System.out.printf("%-30s %-20d\n", cliente.getNome(), totalPedidos);
                System.out.println("-------------------------------------------------------------------------");
            } else {
                System.out.println("Nenhum cliente encontrado.");
            }
        } catch (NoResultException e) {
            System.out.println("Nenhum cliente encontrado.");
        }
    }

    private void gerarRelatorio() {
        List<Cliente> clientes = clienteRepository.findAll();
        clientes.forEach(this::exibirDetalhesCliente);
        clientes.forEach(this::exibirPedidosCliente);
    }

    private void gerarRelatorioClientesAtivosEVendas() {
        YearMonth currentMonth = YearMonth.now();
        LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();

        // Total de clientes ativos (clientes com pelo menos um pedido no mês)
        Long totalClientesAtivos = clienteRepository.countClientesAtivos(startDate, endDate);

        // Total vendido no mês
        BigDecimal totalVendas = itemPedidoRepository.totalVendasNoMes(startDate, endDate);

        System.out.println("-------------------------------------------------");
        System.out.println(" RELATÓRIO DE CLIENTES ATIVOS E TOTAL VENDIDO NO MÊS ");
        System.out.println("-------------------------------------------------");
        System.out.printf("Total de clientes ativos no mês: %d\n", totalClientesAtivos);
        System.out.printf("Total vendido no mês: R$ %.2f\n", totalVendas);
        System.out.println("-------------------------------------------------");
    }

    private void exibirDetalhesCliente(Cliente cliente) {
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

    private void exibirPedidosCliente(Cliente cliente) {
        List<Pedido> pedidos = pedidoRepository.findByClienteIdWithItensPedidos(cliente.getId());

        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado para este cliente.");
        } else {
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("Pedidos do Cliente:");
            pedidos.forEach(pedido -> {
                System.out.println("Pedido ID: " + pedido.getId() + " | Data: " + pedido.getData() + " | Total: " + pedido.getValorTotal());
                System.out.println("Itens do Pedido:");
                System.out.printf("%-30s %-10s %-15s %-15s\n", "Produto", "Quantidade", "Preço Unitário", "Valor Total");
                pedido.getItensPedidos().forEach(item -> {
                    System.out.printf("%-30s %-10d %-15.2f %-15.2f\n",
                            item.getNomeProduto(), item.getQuantidade(), item.getPrecoUnitario(), item.calcularValorTotal());
                });
                System.out.println("-------------------------------------------------------------------------");
            });

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
