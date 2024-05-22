package br.com.alura.comex.repository;

import br.com.alura.comex.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT DISTINCT p FROM Pedido p LEFT JOIN FETCH p.itensPedidos WHERE p.cliente.id = :clienteId")
    List<Pedido> findByClienteIdWithItensPedidos(@Param("clienteId") Long clienteId);
}
