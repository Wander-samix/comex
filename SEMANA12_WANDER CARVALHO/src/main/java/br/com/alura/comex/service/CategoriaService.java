package br.com.alura.comex.service;

import br.com.alura.comex.dto.CategoriaVendaDTO;
import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.model.StatusCategoriaEnum;
import br.com.alura.comex.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> getAllCategorias() {
        return repository.findAll();
    }

    public void cadastro(Categoria novaCategoria) {
        repository.save(novaCategoria);
    }

    public Optional<Categoria> buscaPorId(Long id) {
        return repository.findById(id);
    }

    public List<CategoriaVendaDTO> getVendasPorCategoria() {
        // Implementação omitida
        return null;
    }

    public Optional<Categoria> toggleStatus(Long id) {
        Optional<Categoria> categoria = repository.findById(id);
        categoria.ifPresent(Categoria::toggleStatus);
        categoria.ifPresent(repository::save);
        return categoria;
    }
}
