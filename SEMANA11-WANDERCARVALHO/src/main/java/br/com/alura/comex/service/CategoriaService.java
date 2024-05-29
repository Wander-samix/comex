package br.com.alura.comex.service;

import br.com.alura.comex.dto.CategoriaVendaDTO;
import br.com.alura.comex.model.Categoria;
import br.com.alura.comex.model.ItemDePedido;
import br.com.alura.comex.repository.CategoriaRepository;
import br.com.alura.comex.repository.ItemDePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private ItemDePedidoRepository itemDePedidoRepository;

    public void cadastro(Categoria novaCategoria) {
        if (novaCategoria == null) return;
        if (novaCategoria.getNome() == null) return;

        repository.save(novaCategoria);
    }

    public Optional<Categoria> buscaPorId(Long categoriaId) {
        return repository.findById(categoriaId);
    }

    public List<CategoriaVendaDTO> getVendasPorCategoria() {
        List<Categoria> categorias = (List<Categoria>) repository.findAll();

        return categorias.stream().map(categoria -> {
            List<ItemDePedido> itens = itemDePedidoRepository.findByProdutoCategoria(categoria);
            Long quantidadeVendida = itens.stream().mapToLong(ItemDePedido::getQuantidade).sum();
            BigDecimal montanteVendido = itens.stream()
                    .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return new CategoriaVendaDTO(categoria.getNome(), quantidadeVendida, montanteVendido);
        }).collect(Collectors.toList());
    }
}
