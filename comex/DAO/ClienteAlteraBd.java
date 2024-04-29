package br.com.alura.comex.DAO;

import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.Endereco;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ClienteAlteraBd {

    private final EntityManager entityManager;

    public ClienteAlteraBd(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void atualizar(Cliente cliente) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }

            // Verifica se o endereço do cliente já está persistido
            if (cliente.getEndereco().getId() == null) {
                // Se o endereço não está persistido, salva-o primeiro
                Endereco endereco = cliente.getEndereco();
                entityManager.persist(endereco);
            }

            entityManager.merge(cliente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao atualizar o cliente", e);
        }
    }

    public void salvar(Cliente cliente) {
    }

    public void excluir(Cliente cliente) {
    }
}