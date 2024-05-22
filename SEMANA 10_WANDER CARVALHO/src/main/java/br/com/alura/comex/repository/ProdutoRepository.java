package br.com.alura.comex.repository;

import br.com.alura.comex.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Produto p SET p.quantidade = p.quantidade - :quantidade WHERE p.id = :produtoId")
    void atualizarEstoque(Long produtoId, int quantidade);
}
