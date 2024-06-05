package br.com.alura.comex.service;

import br.com.alura.comex.dto.ClienteResumoDTO;
import br.com.alura.comex.dto.ItemPedidoDTO;
import br.com.alura.comex.dto.ItemDetalhaPedido;
import br.com.alura.comex.model.Pedido;
import br.com.alura.comex.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService<ClienteResumoDTO> {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Optional<ItemDetalhaPedido> buscaPedidoPorId(Long pedidoId) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedidoId);

        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();

            List<ItemPedidoDTO> produtos = pedido.getItemPedidos().stream().map(item -> new ItemPedidoDTO(
                    item.getId(),
                    item.getProduto().getNome(),
                    item.getProduto().getCategoria().getNome(),
                    item.getQuantidade(),
                    item.getPrecoUnitario(),
                    item.getTotal(),
                    item.getDesconto()
            )).collect(Collectors.toList());

            br.com.alura.comex.dto.ClienteResumoDTO clienteDTO;
            clienteDTO = new br.com.alura.comex.dto.ClienteResumoDTO(pedido.getCliente().getId(), pedido.getCliente().getNome());

            ItemDetalhaPedido pedidoDetalhadoDTO = new ItemDetalhaPedido(
                    pedido.getData(),
                    pedido.getTotal(),
                    pedido.getDesconto(),
                    produtos,
                    (br.com.alura.comex.dto.ClienteResumoDTO) clienteDTO
            );

            return Optional.of(pedidoDetalhadoDTO);
        }

        return Optional.empty();
    }
}
