package br.com.alura.comex.repository;

import br.com.alura.comex.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    static void cadastrar(Categoria novaCategoria) {

    }

    // Busca uma categoria por ID - já fornecido pelo JpaRepository

    // Lista todas as categorias - já fornecido pelo JpaRepository

    // Busca uma categoria por nome
    Categoria findByNome(String nome);

    // Relatório de vendas por categoria
    @Query("SELECT c.nome, COUNT(p.id), SUM(ip.quantidade * ip.precoUnitario) " +
            "FROM Categoria c " +
            "JOIN c.produtos p " +
            "JOIN p.itensPedido ip " +
            "GROUP BY c.nome")
    List<Object[]> relatorioDeVendasPorCategoria();
}
