import br.com.alura.comex.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestaPedidoOrdenado {

    public static void main(String[] args) {
        // Criar categorias
        Categoria categoria1 = new Categoria("Eletrônicos");
        Categoria categoria2 = new Categoria("Móveis");

        // Criar clientes
        Cliente cliente1 = new Cliente("Fulano", "123.456.789-00", "fulano@gmail.com", "Desenvolvedor", "123456789", new Endereco("Rua A", "123", "Bairro X", "Cidade Y", "12345-678"));
        Cliente cliente2 = new Cliente("Ciclano", "987.654.321-00", "ciclano@gmail.com", "Analista", "987654321", new Endereco("Rua B", "456", "Bairro Y", "Cidade Z", "98765-432"));

        // Criar produtos
        Produto produto1 = new Produto("Notebook", "Notebook Dell", new BigDecimal("3000.0"), 10, categoria1);
        Produto produto2 = new Produto("Cadeira", "Cadeira de escritório", new BigDecimal("500.0"), 20, categoria2);

        // Criar pedidos com variações de valores e produtos, associando a cada cliente
        Pedido pedido1 = criarPedido(cliente1, produto1, 2, TipoDesconto.NENHUM);
        Pedido pedido2 = criarPedido(cliente2, produto2, 3, TipoDesconto.PROMOCIONAL);
        Pedido pedido3 = criarPedido(cliente1, produto1, 1, TipoDesconto.FIDELIDADE);
        Pedido pedido4 = criarPedido(cliente2, produto2, 5, TipoDesconto.PROMOCIONAL);
        Pedido pedido5 = criarPedido(cliente1, produto1, 1, TipoDesconto.NENHUM);

        // Atribuir os pedidos criados a uma lista
        List<Pedido> pedidos = new ArrayList<>(List.of(pedido1, pedido2, pedido3, pedido4, pedido5));

        // Ordenar a lista de pedidos pelo maior valor total
        Pedido pedidoMaiorValor = pedidos.stream()
                .max(Comparator.comparing(Pedido::getValorTotal))
                .orElse(null);

        System.out.println("Pedido com maior valor total:");
        if (pedidoMaiorValor != null) {
            System.out.println(pedidoMaiorValor);
        } else {
            System.out.println("Nenhum pedido encontrado.");
        }

        // Ordenar a lista de pedidos pelo menor valor total
        Pedido pedidoMenorValor = pedidos.stream()
                .min(Comparator.comparing(Pedido::getValorTotal))
                .orElse(null);

        System.out.println("\nPedido com menor valor total:");
        if (pedidoMenorValor != null) {
            System.out.println(pedidoMenorValor);
        } else {
            System.out.println("Nenhum pedido encontrado.");
        }
    }

    private static Pedido criarPedido(Cliente cliente, Produto produto, int quantidade, TipoDesconto tipoDesconto) {
        List<ItemPedido> itensPedido = new ArrayList<>();
        itensPedido.add(new ItemPedido(produto, quantidade, produto.getPrecoUnitario(), tipoDesconto, null)); // O pedido será setado depois
        Pedido pedido = new Pedido(cliente, produto.getPrecoUnitario().multiply(BigDecimal.valueOf(quantidade)), quantidade, LocalDate.now(), tipoDesconto, itensPedido);
        for (ItemPedido item : itensPedido) {
            item.setPedido(pedido);
        }
        return pedido;
    }
}
