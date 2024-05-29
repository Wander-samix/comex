package br.com.alura.comex.controller;

import br.com.alura.comex.dto.ProdutoDTO;
import br.com.alura.comex.model.DadosNovoProduto;
import br.com.alura.comex.model.Produto;
import br.com.alura.comex.service.CategoriaService;
import br.com.alura.comex.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<?> cadastra(@RequestBody @Valid DadosNovoProduto form, BindingResult result) {
        if (result.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        try {
            Produto novoProduto = form.toEntity(categoriaService);
            produtoService.cadastra(novoProduto);
            return ResponseEntity.ok().body(novoProduto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/listaprodutos")
    public ResponseEntity<Page<ProdutoDTO>> listaProdutos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<Produto> produtosPage = produtoService.listaPaginada(page, size);
        Page<ProdutoDTO> produtosDTOPage = produtosPage.map(ProdutoDTO::new);
        return ResponseEntity.ok(produtosDTOPage);
    }
}
