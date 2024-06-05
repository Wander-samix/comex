package br.com.alura.comex.controller;

import br.com.alura.comex.dto.CategoriaVendaDTO;
import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @PostMapping
    public ResponseEntity<String> cadastra(@RequestBody Categoria novaCategoria) {
        if (novaCategoria.getNome() == null) {
            return ResponseEntity.badRequest().body("Necessário o parâmetro 'nome'");
        }

        service.cadastro(novaCategoria);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscaPorId(@PathVariable("id") Long categoriaId) {
        Optional<Categoria> categoria = service.buscaPorId(categoriaId);

        if (categoria.isPresent()) return ResponseEntity.ok().body(categoria.get());

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/vendas")
    public ResponseEntity<List<CategoriaVendaDTO>> getVendasPorCategoria() {
        List<CategoriaVendaDTO> vendas = service.getVendasPorCategoria();
        return ResponseEntity.ok(vendas);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> toggleStatus(@PathVariable("id") Long categoriaId) {
        Optional<Categoria> categoria = service.toggleStatus(categoriaId);

        if (categoria.isPresent()) return ResponseEntity.ok().body(categoria.get());

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/ws/categorias")
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        List<Categoria> categorias = service.getAllCategorias();
        return ResponseEntity.ok(categorias);
    }
}
