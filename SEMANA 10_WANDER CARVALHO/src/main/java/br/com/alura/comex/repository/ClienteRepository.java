package br.com.alura.comex.repository;

import br.com.alura.comex.model.Cliente;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByCpf(String cpf);

    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.pedidos WHERE c.cpf = :cpf")
    Cliente findByCpfWithPedidos(@Param("cpf") String cpf);

    @Query("SELECT DISTINCT c FROM Cliente c LEFT JOIN FETCH c.pedidos")
    List<Cliente> findAllWithPedidos();

    @Query("SELECT c.id, COUNT(p) as totalPedidos FROM Cliente c JOIN c.pedidos p GROUP BY c.id ORDER BY totalPedidos DESC")
    List<Object[]> findClienteMaisFiel(Pageable pageable);

    @Query("SELECT COUNT(DISTINCT p.cliente) FROM Pedido p WHERE p.data BETWEEN :startDate AND :endDate")
    Long countClientesAtivos(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}

