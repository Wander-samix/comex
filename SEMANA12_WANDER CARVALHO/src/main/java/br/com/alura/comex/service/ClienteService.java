package br.com.alura.comex.service;

import br.com.alura.comex.dto.ClienteDTO;
import br.com.alura.comex.model.Cliente;
import br.com.alura.comex.model.Endereco;
import br.com.alura.comex.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public void cadastra(ClienteDTO clienteDTO) {
        Endereco endereco = new Endereco();
        endereco.setRua(clienteDTO.getRua());
        endereco.setNumero(clienteDTO.getNumero());
        endereco.setComplemento(clienteDTO.getComplemento());
        endereco.setBairro(clienteDTO.getBairro());
        endereco.setCidade(clienteDTO.getCidade());
        endereco.setEstado(clienteDTO.getEstado());

        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setEndereco(endereco);

        repository.save(cliente);
    }

    public void deleta(Cliente cliente) {
        repository.delete(cliente);
    }

    public List<Cliente> listaTodos() {
        return repository.findAll();
    }

    public Page<ClienteDTO> listarClientes(Pageable pageable) {
        return repository.findAllByOrderByNome(pageable)
                .map(cliente -> new ClienteDTO(
                        cliente.getNome(),
                        cliente.getCpf(),
                        cliente.getTelefone(),
                        cliente.getEndereco().getCidade() + "/" + cliente.getEndereco().getEstado()
                ));
    }
}
