package br.com.alura.comex.controller;

import br.com.alura.comex.dto.ItemDetalhaPedido;
import br.com.alura.comex.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<ItemDetalhaPedido> buscaPedidoPorId(@PathVariable Long id) {
        Optional<ItemDetalhaPedido> pedidoOpt = pedidoService.buscaPedidoPorId(id);

        return pedidoOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
