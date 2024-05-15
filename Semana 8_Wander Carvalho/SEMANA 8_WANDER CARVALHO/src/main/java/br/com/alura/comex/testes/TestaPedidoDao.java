package br.com.alura.comex.testes;

import br.com.alura.comex.dao.CategoriaDao;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TestaPedidoDao {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("oracle");
        EntityManager em = emf.createEntityManager();

        CategoriaDao categoriaDao = new CategoriaDao(em);

        List<Object[]> relatorio = categoriaDao.relatorioDeVendasPorCategoria();
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-30s %-20s %-15s %n", "Categoria", "Produtos Vendidos", "Montante Vendido");
        System.out.println("-------------------------------------------------------------------------");
        relatorio.forEach(linha -> {
            String nomeCategoria = (String) linha[0];
            Long quantidadeProdutos = (Long) linha[1];
            Double montanteVendido = (Double) linha[2];
            System.out.printf("%-30s %-20d %-15.2f %n", nomeCategoria, quantidadeProdutos, montanteVendido);
        });
        System.out.println("-------------------------------------------------------------------------");

        em.close();
        emf.close();
    }
}