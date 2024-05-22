import br.com.alura.comex.model.RelatorioCliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static java.sql.DriverManager.getConnection;
import static oracle.jdbc.util.OracleEnvironment.SID;

public class TestaPedidoDao {

    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:1521";
        String usuario = "system";
        String senha = "12345678";

        try (Connection connection = DriverManager.getConnection(url, usuario, senha)) {
            RelatorioCliente relatorioClienteDao = new RelatorioCliente(connection);
            List<RelatorioCliente> relatorioClientes = relatorioClienteDao.gerarRelatorioCliente();

            System.out.println("Relatório dos Três Clientes que Mais Gastaram:");
            for (RelatorioCliente relatorioCliente : relatorioClientes) {
                System.out.println("Nome do Cliente: " + relatorioCliente.getNomeCliente());
                System.out.println("Quantidade de Pedidos: " + relatorioCliente.getQuantidadePedidos());
                System.out.println("Montante Gasto: " + relatorioCliente.getMontanteGasto());
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}