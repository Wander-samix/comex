package br.com.alura.comex.testes;

import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.Pedido;
import br.com.alura.comex.model.Produto;
import br.com.alura.comex.model.ItemPedido;
import br.com.alura.comex.model.TipoDesconto;
import br.com.alura.comex.model.Categoria;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrdenacaoPedido {
    public static void main(String... args) {
        List<Pedido> pedidos = new ArrayList<>();
        Cliente clienteJoao = new Cliente("João Silva", "123.456.789-00", "joao@gmail.com", "Engenheiro", "9988776655", null);
        Cliente clienteMaria = new Cliente("Maria Fernanda", "987.654.321-00", "maria@gmail.com", "Advogada", "9966554433", null);
        Cliente clienteCarlos = new Cliente("Carlos Eduardo", "321.654.987-00", "carlos@gmail.com", "Médico", "9944221100", null);
        Cliente clienteAna = new Cliente("Ana Paula", "321.654.987-00", "ana@gmail.com", "Designer", "9944221100", null);
        Cliente clientePaulo = new Cliente("Paulo Henrique", "321.654.987-00", "paulo@gmail.com", "Fotógrafo", "9944221100", null);

        // Criar categorias
        Categoria categoria1 = new Categoria("Eletrônicos");
        Categoria categoria2 = new Categoria("Móveis");

        // Criar produtos
        Produto produto1 = new Produto("Notebook", "Notebook Dell", new BigDecimal("3000.0"), 10, categoria1);
        Produto produto2 = new Produto("Cadeira", "Cadeira de escritório", new BigDecimal("500.0"), 20, categoria2);

        // Adicionando pedidos com itens do pedido
        pedidos.add(criarPedido(clienteJoao, produto1, 2, new BigDecimal("1200.00")));
        pedidos.add(criarPedido(clienteMaria, produto2, 1, new BigDecimal("300.00")));
        pedidos.add(criarPedido(clienteCarlos, produto1, 3, new BigDecimal("450.00")));
        pedidos.add(criarPedido(clienteAna, produto2, 1, new BigDecimal("850.00")));
        pedidos.add(criarPedido(clientePaulo, produto1, 2, new BigDecimal("1250.00")));

        // Ordenando por maior valor total
        Collections.sort(pedidos, Comparator.comparing(Pedido::getValorTotal).reversed());
        System.out.println("\nPedidos ordenados por maior valor total:");
        for (Pedido pedido : pedidos) {
            System.out.println("Pedido ID: " + pedido.getId() + ", Valor Total: " + pedido.getValorTotal());
        }

        // Ordenando por menor valor total
        Collections.sort(pedidos, Comparator.comparing(Pedido::getValorTotal));
        System.out.println("\nPedidos ordenados por menor valor total:");
        for (Pedido pedido : pedidos) {
            System.out.println("Pedido ID: " + pedido.getId() + ", Valor Total: " + pedido.getValorTotal());
        }
    }

    private static Pedido criarPedido(Cliente cliente, Produto produto, int quantidade, BigDecimal preco) {
        List<ItemPedido> itensPedido = new ArrayList<>();
        TipoDesconto tipoDesconto = TipoDesconto.NENHUM; // Assumindo que não há desconto se não especificado
        itensPedido.add(new ItemPedido(produto, quantidade, preco, tipoDesconto, null)); // O pedido será setado depois
        Pedido pedido = new Pedido(cliente, preco.multiply(BigDecimal.valueOf(quantidade)), quantidade, LocalDate.now(), tipoDesconto, itensPedido);
        for (ItemPedido item : itensPedido) {
            item.setPedido(pedido);
        }
        return pedido;
    }
}
