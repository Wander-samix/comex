package br.com.alura.comex.dao;

import br.com.alura.comex.model.Produto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;
import static javax.persistence.Persistence.createEntityManagerFactory;

public class TodosProdutosCadastradosBd {

    private EntityManager entityManager;

    public TodosProdutosCadastradosBd(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Produto> listarTodos() {
        TypedQuery<Produto> query = entityManager.createQuery("SELECT p FROM Produto p", Produto.class);
        return query.getResultList();
    }

    public static void main(String[] args) {
        EntityManagerFactory factory = createEntityManagerFactory("oracle");
        EntityManager entityManager = factory.createEntityManager();

        TodosProdutosCadastradosBd produtoDAO = new TodosProdutosCadastradosBd(entityManager);

        List<Produto> produtos = produtoDAO.listarTodos();

        // Gerar relatório
        gerarRelatorio(produtos);

        entityManager.close();
        factory.close();
    }

    private static void gerarRelatorio(List<Produto> produtos) {
        System.out.println("-------------------------------------------------");
        System.out.println("              RELATÓRIO DE PRODUTOS              ");
        System.out.println("-------------------------------------------------");
        System.out.printf("%-5s %-30s %-20s %-10s %-10s %-10s\n", "ID", "Nome", "Descrição", "Preço Unit.", "Quantidade", "Categoria");
        System.out.println("-------------------------------------------------");

        for (Produto produto : produtos) {
            System.out.printf("%-5d %-30s %-20s %-10.2f %-10d %-10s\n",
                    produto.getId(),
                    produto.getNome(),
                    produto.getDescricao(),
                    produto.getPrecoUnitario(),
                    produto.getQuantidade(),
                    produto.getCategoria().getNome());
        }

        System.out.println("-------------------------------------------------");
    }
}
