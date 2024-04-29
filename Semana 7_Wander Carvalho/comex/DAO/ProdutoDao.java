package br.com.alura.comex.DAO;

import br.com.alura.comex.model.Produto;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ProdutoDao {
    private final EntityManager em;

    public ProdutoDao(EntityManager em) {
        this.em = em;
    }

    // Busca um produto por ID
    public Produto buscaPorId(Long id) {
        Produto produto = em.find(Produto.class, id);
        System.out.println("Busca por ID - Produto encontrado: " + produto);
        return produto;
    }

    // Cadastra um novo produto
    public void cadastra(Produto produto) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(produto);
            et.commit();
            System.out.println("Cadastrando Produto: " + produto);
        } catch (Exception e) {
            et.rollback();
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    // Lista todos os produtos
    public List<Produto> listaTodos() {
        List<Produto> produtos = em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
        System.out.println("Listando todos os Produtos: " + produtos);
        return produtos;
    }

    // Lista produtos que estão indisponíveis (quantidade = 0)
    public List<Produto> listaIndisponiveis() {
        List<Produto> produtosIndisponiveis = em.createQuery("SELECT p FROM Produto p WHERE p.quantidade = 0", Produto.class).getResultList();
        System.out.println("Listando produtos indisponíveis: " + produtosIndisponiveis);
        return produtosIndisponiveis;
    }
}
