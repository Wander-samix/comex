package br.com.alura.comex.dao;

import br.com.alura.comex.model.Categoria;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class CategoriaDao {
    private final EntityManager em;

    public CategoriaDao(EntityManager em) {
        this.em = em;
    }

    // Busca uma categoria por ID
    public Categoria buscarPorId(Long id) {
        Categoria categoria = em.find(Categoria.class, id);
        System.out.println("Buscar por ID - Categoria encontrada: " + categoria);
        return categoria;
    }

    // Cadastra uma nova categoria
    public void cadastra(Categoria categoria) {
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
}
