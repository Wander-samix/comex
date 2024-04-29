package br.com.alura.comex.DAO;

import br.com.alura.comex.model.Cliente;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ClienteDao {
    private final EntityManager em;

    public ClienteDao(EntityManager em) {
        this.em = em;
    }

    // Busca um cliente por seu ID no banco de dados
    public Cliente buscaPorId(Long id) {
        Cliente cliente = em.find(Cliente.class, id);
        System.out.println("Busca por ID - Cliente encontrado: " + cliente);
        return cliente;
    }

    // Cadastra um novo cliente no banco de dados
    public void cadastra(Cliente cliente) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(cliente);
            et.commit();
            System.out.println("Cadastrando Cliente: " + cliente);
        } catch (Exception e) {
            et.rollback();
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    // Atualiza os dados de um cliente existente no banco de dados
    public void atualiza(Cliente cliente) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.merge(cliente);
            et.commit();
            System.out.println("Atualizando Cliente: " + cliente);
        } catch (Exception e) {
            et.rollback();
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    // Remove um cliente do banco de dados
    public void remove(Cliente cliente) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.remove(em.contains(cliente) ? cliente : em.merge(cliente));
            et.commit();
            System.out.println("Removendo Cliente: " + cliente);
        } catch (Exception e) {
            et.rollback();
            System.out.println("Erro ao remover cliente: " + e.getMessage());
        }
    }

    // Lista todos os clientes cadastrados no banco de dados
    public List<Cliente> listaTodos() {
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        System.out.println("Listando todos os Clientes: " + clientes);
        return clientes;
    }

    // Lista clientes por nome, usando uma busca parcial
    public List<Cliente> listaPorNome(String nome) {
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c WHERE c.nome LIKE :nome", Cliente.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
        System.out.println("Listando Clientes por nome (" + nome + "): " + clientes);
        return clientes;
    }
}


