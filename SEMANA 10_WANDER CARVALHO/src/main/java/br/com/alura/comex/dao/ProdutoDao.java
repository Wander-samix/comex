package br.com.alura.comex.dao;

import br.com.alura.comex.model.Produto;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ProdutoDao {
    private final EntityManager em;

    public ProdutoDao(EntityManager em) {
        this.em = em;
    }

    public Produto buscaPorId(Long id) {
        return em.find(Produto.class, id);
    }

    public void cadastra(Produto produto) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(produto);
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new RuntimeException("Erro ao cadastrar produto", e);
        }
    }

    public List<Produto> listaTodos() {
        return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
    }

    public List<Produto> listaIndisponiveis() {
        return em.createQuery("SELECT p FROM Produto p WHERE p.quantidade = 0", Produto.class).getResultList();
    }

    /**
     * Atualiza a quantidade em estoque de um produto específico.
     *
     * @param produtoId O ID do produto a ser atualizado.
     * @param novaQuantidade A nova quantidade do produto.
     */
    public void atualizarEstoque(Long produtoId, int novaQuantidade) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            Produto produto = em.find(Produto.class, produtoId);
            if (produto != null) {
                produto.setQuantidade(novaQuantidade);
                em.merge(produto);
                System.out.println("Estoque atualizado - Produto: " + produto.getNome() + ", Quantidade: " + novaQuantidade);
            } else {
                System.out.println("Produto não encontrado para atualização de estoque.");
            }
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            throw new RuntimeException("Erro ao atualizar o estoque do produto", e);
        }
    }

    public void salvar(Produto produto) {
    }

    public List<Produto> buscarTodos() {
        return List.of();
    }
}
