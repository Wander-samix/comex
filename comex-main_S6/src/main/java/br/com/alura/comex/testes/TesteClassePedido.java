package br.com.alura.comex.testes;

import br.com.alura.comex.cliente.Cliente;
import br.com.alura.comex.pedido.Pedido;
import br.com.alura.comex.produto.Produto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TesteClassePedido {
    public static void main(String[] args) {
        // Criando clientes
        Cliente cliente1 = new Cliente("João Silva", "123.456.789-00", "joao.silva@gmail.com", "Engenheiro", "51 99876-5432", null);
        Cliente cliente2 = new Cliente("Maria Oliveira", "987.654.321-00", "maria.oliveira@gmail.com", "Médica", "21 98765-4321", null);

        // Criando produtos (simplificado, apenas o nome para exemplificar)
        Produto produto1 = new Produto("Notebook", "Notebook Dell", 3500.00, 10);
        Produto produto2 = new Produto("Smartphone", "iPhone 13", 5000.00, 5);
        Produto produto3 = new Produto("Impressora", "Impressora HP", 800.00, 15);

        // Criando pedidos
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido(101, cliente1, new BigDecimal("3500.00"), 1));
        pedidos.add(new Pedido(102, cliente1, new BigDecimal("7000.00"), 2));  // 2 notebooks
        pedidos.add(new Pedido(103, cliente2, new BigDecimal("5000.00"), 1));
        pedidos.add(new Pedido(104, cliente2, new BigDecimal("1600.00"), 2));  // 2 impressoras
        pedidos.add(new Pedido(105, cliente1, new BigDecimal("800.00"), 1));

        // Exibindo informações dos pedidos
        for (Pedido pedido : pedidos) {
            System.out.println("Produto: " + pedido.getCliente().getNome() + ", Cliente: " + pedido.getCliente().getNome() +
                    ", Preço: " + pedido.getPreco() + ", Valor Total: " + pedido.getValorTotal());
        }
    }
}


