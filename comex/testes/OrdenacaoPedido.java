package br.com.alura.comex.testes;


import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.Pedido;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrdenacaoPedido {
    public static void main(String... args){
        // Criação de pedidos simulada, substitua isto pelo código de criação dos pedidos
        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido(101, new Cliente("João Silva", "123.456.789-00", "joao@gmail.com", "Engenheiro", "9988776655", null), new BigDecimal("1200.00"), 2));
        pedidos.add(new Pedido(102, new Cliente("Maria Fernanda", "987.654.321-00", "maria@gmail.com", "Advogada", "9966554433", null), new BigDecimal("300.00"), 1));
        pedidos.add(new Pedido(103, new Cliente("Carlos Eduardo", "321.654.987-00", "carlos@gmail.com", "Médico", "9944221100", null), new BigDecimal("450.00"), 3));
        pedidos.add(new Pedido(104, new Cliente("Ana Paula", "321.654.987-00", "ana@gmail.com", "Designer", "9944221100", null), new BigDecimal("850.00"), 1));
        pedidos.add(new Pedido(105, new Cliente("Paulo Henrique", "321.654.987-00", "paulo@gmail.com", "Fotógrafo", "9944221100", null), new BigDecimal("1250.00"), 2));

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
}

