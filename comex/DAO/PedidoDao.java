package br.com.alura.comex.DAO;

import br.com.alura.comex.model.Pedido;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class PedidoDao {
    private final EntityManager em;

    public PedidoDao(EntityManager em) {
        this.em = em;
    }

    // Busca um pedido por ID
    public Pedido buscaPorId(Long id) {
        Pedido pedido = em.find(Pedido.class, id);
        System.out.println("Busca por ID - Pedido encontrado: " + pedido);
        return pedido;
    }

    // Cadastra um novo pedido
    public void cadastra(Pedido pedido) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(pedido);
            et.commit();
            System.out.println("Cadastrando Pedido: " + pedido);
        } catch (Exception e) {
            et.rollback();
            System.out.println("Erro ao cadastrar pedido: " + e.getMessage());
        }
    }

    // Lista todos os pedidos
    public List<Pedido> listaTodos() {
        List<Pedido> pedidos = em.createQuery("SELECT p FROM Pedido p", Pedido.class).getResultList();
        System.out.println("Listando todos os Pedidos: " + pedidos);
        return pedidos;
    }
}
