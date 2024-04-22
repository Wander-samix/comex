import br.com.alura.comex.cliente.Cliente;
import br.com.alura.comex.pedido.Pedido;

import java.math.BigDecimal;

// Classe TestePedido
public class ComparaProduto {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("João Silva", "123.456.789-00", "joao.silva@gmail.com", "Engenheiro", "51 99876-5432", null);
        Pedido pedido1 = new Pedido(101, cliente, new BigDecimal("150.00"), 2);
        Pedido pedido2 = new Pedido(102, cliente, new BigDecimal("200.00"), 1);

        System.out.println(pedido1);
        System.out.println(pedido2);

        // Usando operador ternário para converter boolean para "Sim" ou "Não"
        String maisCaro = pedido1.isMaisCaroQue(pedido2) ? "Sim" : "Não";
        String maisBarato = pedido1.isMaisBaratoQue(pedido2) ? "Sim" : "Não";

        System.out.println("Pedido 1 é mais caro que Pedido 2? " + maisCaro);
        System.out.println("Pedido 1 é mais barato que Pedido 2? " + maisBarato);
    }
}
