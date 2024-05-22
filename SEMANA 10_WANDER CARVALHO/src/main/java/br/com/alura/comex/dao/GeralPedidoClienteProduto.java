package br.com.alura.comex.dao;
import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.Pedido;
import br.com.alura.comex.model.Produto;

import javax.persistence.EntityManager;
import java.util.List;

public class GeralPedidoClienteProduto {

    public class ClienteDao {
        private EntityManager em;
        public ClienteDao(EntityManager em) {
            this.em = em;
        }

        public void salvar(Cliente cliente) {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        }

        public List<Cliente> buscarTodos() {
            return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        }
    }



    public class PedidoDao {
        private EntityManager em;
        public PedidoDao(EntityManager em) {
            this.em = em;
        }

        public void salvar(Pedido pedido) {
            em.getTransaction().begin();
            em.persist(pedido);
            em.getTransaction().commit();
        }

        public List<Pedido> buscarTodos() {
            return em.createQuery("SELECT p FROM Pedido p", Pedido.class).getResultList();
        }
    }

    public class ProdutoDao {
        private EntityManager em;
        public ProdutoDao(EntityManager em) {
            this.em = em;
        }

        public void salvar(Produto produto) {
            em.getTransaction().begin();
            em.persist(produto);
            em.getTransaction().commit();
        }

        public void atualizarEstoque(Produto produto, int quantidade) {
            em.getTransaction().begin();
            produto.setQuantidade(produto.getQuantidade() + quantidade);
            em.merge(produto);
            em.getTransaction().commit();
        }

        public List<Produto> buscarTodos() {
            return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
        }
    }

}