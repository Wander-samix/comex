package br.com.alura.comex.testes;

import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.repository.CategoriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestaPedidoDao {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("oracle");
        EntityManager em = emf.createEntityManager();

        CategoriaRepository categoriaRepository = new CategoriaRepository() {
            @Override
            public List<Categoria> findAll(Sort sort) {
                return List.of();
            }

            @Override
            public Page<Categoria> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Categoria> S save(S entity) {
                return null;
            }

            @Override
            public <S extends Categoria> List<S> saveAll(Iterable<S> entities) {
                return List.of();
            }

            @Override
            public Optional<Categoria> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public List<Categoria> findAll() {
                return List.of();
            }

            @Override
            public List<Categoria> findAllById(Iterable<Long> longs) {
                return List.of();
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(Categoria entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Long> longs) {

            }

            @Override
            public void deleteAll(Iterable<? extends Categoria> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Categoria> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends Categoria> List<S> saveAllAndFlush(Iterable<S> entities) {
                return List.of();
            }

            @Override
            public void deleteAllInBatch(Iterable<Categoria> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Long> longs) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Categoria getOne(Long aLong) {
                return null;
            }

            @Override
            public Categoria getById(Long aLong) {
                return null;
            }

            @Override
            public Categoria getReferenceById(Long aLong) {
                return null;
            }

            @Override
            public <S extends Categoria> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Categoria> List<S> findAll(Example<S> example) {
                return List.of();
            }

            @Override
            public <S extends Categoria> List<S> findAll(Example<S> example, Sort sort) {
                return List.of();
            }

            @Override
            public <S extends Categoria> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Categoria> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Categoria> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends Categoria, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }

            @Override
            public Categoria findByNome(String nome) {
                return null;
            }

            @Override
            public List<Object[]> relatorioDeVendasPorCategoria() {
                return List.of();
            }
        };

        List<Object[]> relatorio = categoriaRepository.relatorioDeVendasPorCategoria();
        System.out.println("-------------------------------------------------------------------------");
        System.out.printf("%-30s %-20s %-15s %n", "Categoria", "Produtos Vendidos", "Montante Vendido");
        System.out.println("-------------------------------------------------------------------------");
        relatorio.forEach(linha -> {
            String nomeCategoria = (String) linha[0];
            Long quantidadeProdutos = (Long) linha[1];
            Double montanteVendido = (Double) linha[2];
            System.out.printf("%-30s %-20d %-15.2f %n", nomeCategoria, quantidadeProdutos, montanteVendido);
        });
        System.out.println("-------------------------------------------------------------------------");

        em.close();
        emf.close();
    }
}