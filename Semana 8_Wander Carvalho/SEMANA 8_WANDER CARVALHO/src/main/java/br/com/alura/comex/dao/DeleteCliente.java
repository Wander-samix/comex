import br.com.alura.comex.dao.ClienteDao;
import br.com.alura.comex.model.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class DeleteCliente {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("oracle");
        EntityManager em = emf.createEntityManager();
        ClienteDao clienteDao = new ClienteDao(em);

        try {
            // Verifica se a transação já está ativa antes de começar uma nova
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            List<Cliente> clientes = clienteDao.buscarTodos();
            if (clientes.isEmpty()) {
                System.out.println("Não há clientes cadastrados.");
                // Certifique-se de fechar a transação se não houver clientes
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
            } else {
                System.out.println("Selecione o ID do cliente que deseja deletar:");
                for (Cliente cliente : clientes) {
                    System.out.printf("ID: %d, Nome: %s, CPF: %s\n", cliente.getId(), cliente.getNome(), cliente.getCpf());
                }

                Scanner scanner = new Scanner(System.in);
                System.out.print("Digite o ID do cliente: ");
                long clienteId = scanner.nextLong();
                scanner.close();

                clienteDao.remove(clienteId); // Assume que este método não faz commit ou rollback internamente

                // Verifica se a transação ainda pode ser commitada
                if (em.getTransaction().isActive() && !em.getTransaction().getRollbackOnly()) {
                    em.getTransaction().commit();
                } else {
                    em.getTransaction().rollback();
                }
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em.isOpen()) em.close();
            if (emf.isOpen()) emf.close();
        }
    }
}
