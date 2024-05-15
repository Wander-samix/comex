package br.com.alura.comex.dao;

import br.com.alura.comex.model.Categoria;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class CategoriaDao {
    private EntityManager em;

    public CategoriaDao(EntityManager em) {
        this.em = em;
    }

    public CategoriaDao() {

    }

    // Busca uma categoria por ID
    public Categoria buscarPorId(Long id) {
        Categoria categoria = em.find(Categoria.class, id);
        System.out.println("Buscar por ID - Categoria encontrada: " + categoria);
        return categoria;
    }

    // Cadastra uma nova categoria
    public void cadastrar(Categoria categoria) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(categoria);
            et.commit();
            System.out.println("Cadastrando Categoria: " + categoria);
        } catch (Exception e) {
            et.rollback();
            System.out.println("Erro ao cadastrar categoria: " + e.getMessage());
        }
    }

    // Lista todas as categorias
    public List<Categoria> listaTodas() {
        List<Categoria> categorias = em.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();
        System.out.println("Listando todas as Categorias: " + categorias);
        return categorias;
    }
    public List<Object[]> relatorioDeVendasPorCategoria() {
        return em.createQuery(
                        "SELECT c.nome, COUNT(p.id), SUM(ip.quantidade * ip.precoUnitario) " +
                                "FROM Categoria c " +
                                "JOIN c.produtos p " +
                                "JOIN p.itensPedido ip " +
                                "GROUP BY c.nome", Object[].class)
                .getResultList();

    }

    public Categoria buscarPorNome(String nomeDaCategoria) {
        return null;
    }



}



