package br.com.alura.comex.repository;

import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    @Query("SELECT COALESCE(SUM(i.precoUnitario * i.quantidade), 0) FROM ItemPedido i JOIN i.pedido p WHERE p.data BETWEEN :startDate AND :endDate")
    BigDecimal totalVendasNoMes(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    Cliente getProduto();

    Object getQuantidade();

    Object getPrecoUnitario();

    Object calcularValorTotal();
}
