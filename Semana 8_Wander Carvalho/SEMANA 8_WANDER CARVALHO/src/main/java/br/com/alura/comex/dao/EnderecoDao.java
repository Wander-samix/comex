package br.com.alura.comex.dao;

import br.com.alura.comex.model.Endereco;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class EnderecoDao {

    private final EntityManager entityManager;

    public EnderecoDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void salvar(Endereco endereco) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }

            entityManager.persist(endereco);
            entityManager.flush(); // Sincroniza as alterações com o banco de dados
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao salvar o endereço", e);
        }
    }

    public void atualizar(Endereco endereco) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }

            entityManager.merge(endereco);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao atualizar o endereço", e);
        }
    }

    public void excluir(Endereco endereco) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }

            entityManager.remove(endereco);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao excluir o endereço", e);
        }
    }
}
