package br.com.alura.comex.service;

import br.com.alura.comex.model.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ImprimeDadosClientes {

    public static void main(String[] args) {
        // Certifique-se de que o nome da unidade de persistência corresponde à sua configuração em persistence.xml
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle");
        EntityManager entityManager = factory.createEntityManager();

        try {
            List<Cliente> clientes = findAllClientesWithEndereco(entityManager);
            AtomicInteger counter = new AtomicInteger(1);

            clientes.forEach(cliente -> {
                System.out.printf("Cliente #%d:\n", counter.getAndIncrement());
                System.out.printf("Nome: %s\nCPF: %s\nEmail: %s\nProfissão: %s\nTelefone: %s\n",
                        cliente.getNome(), cliente.getCpf(), cliente.getEmail(), cliente.getProfissao(), cliente.getTelefone());
                System.out.printf("Endereço: %s, Número: %s, Complemento: %s, CEP: %s\n\n",
                        cliente.getEndereco().getLogradouro(), cliente.getEndereco().getNumero(),
                        cliente.getEndereco().getComplemento(), cliente.getEndereco().getCep());
            });
        } finally {
            entityManager.close();
                    }
    }

    private static List<Cliente> findAllClientesWithEndereco(EntityManager em) {
        return em.createQuery("SELECT c FROM Cliente c JOIN FETCH c.endereco", Cliente.class)
                .getResultList();
    }
}
