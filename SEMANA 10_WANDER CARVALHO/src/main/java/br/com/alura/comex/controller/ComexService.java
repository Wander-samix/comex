package br.com.alura.comex.controller;

import br.com.alura.comex.dao.ClienteDao;
import br.com.alura.comex.dao.PedidoDao;
import br.com.alura.comex.dao.ProdutoDao;
import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.Pedido;
import br.com.alura.comex.model.Produto;
import javax.persistence.EntityManager;
import java.util.List;

import javax.persistence.EntityManager;
import java.util.List;

public class ComexService {
    private ClienteDao clienteDao;
    private PedidoDao pedidoDao;
    private ProdutoDao produtoDao;

    public ComexService(EntityManager em) {
        this.clienteDao = new ClienteDao(em);
        this.pedidoDao = new PedidoDao(em);
        this.produtoDao = new ProdutoDao(em);
    }

    public void cadastrarCliente(Cliente cliente) {
        clienteDao.salvar(cliente);
    }

    public void cadastrarPedido(Pedido pedido) {
        pedidoDao.salvar(pedido);
        // Correção: Usando o método correto para obter os itens do pedido
        pedido.getItensPedidos().forEach(item -> {
            produtoDao.atualizarEstoque(item.getProduto(), -item.getQuantidade());
        });
    }

    public void cadastrarProduto(Produto produto) {
        produtoDao.salvar(produto);
    }

    public List<Cliente> listarClientes() {
        return clienteDao.buscarTodos();
    }

    public List<Pedido> listarPedidos() {
        return pedidoDao.buscarTodos();
    }

    public List<Produto> listarProdutos() {
        return produtoDao.buscarTodos();
    }
}
