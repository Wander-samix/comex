package br.com.alura.comex.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class PedidoDTO {

    @NotNull
    private Long clienteId;

    @NotNull
    private List<ItemPedidoDTO> itens;

    public static class ItemPedidoDTO {
        @NotNull
        private Long produtoId;

        @NotNull
        @Positive
        private Long quantidade;

        // Getters e setters
        public Long getProdutoId() {
            return produtoId;
        }

        public void setProdutoId(Long produtoId) {
            this.produtoId = produtoId;
        }

        public Long getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(Long quantidade) {
            this.quantidade = quantidade;
        }
    }

    // Getters e setters
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }
}
