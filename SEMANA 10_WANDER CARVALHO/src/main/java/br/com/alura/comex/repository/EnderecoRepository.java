package br.com.alura.comex.repository;

import br.com.alura.comex.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    // Remover ou corrigir o método 'atualizar' se estiver presente
}
