package br.com.alura.comex.cliente;

import br.com.alura.comex.endereco.Endereco;

public class Cliente {
    // Variáveis de instância privadas para encapsular os dados do cliente
    private String nome;
    private String cpf;
    private String email;
    private String profissao;
    private String telefone;
    private Endereco endereco;

    // Construtor para inicializar um objeto Cliente com os valores passados como parâmetros
    public Cliente(String nome, String cpf, String email, String profissao, String telefone, Endereco endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.profissao = profissao;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    // Métodos getters para acesso aos dados do cliente
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public String getProfissao() { return profissao; }
    public String getTelefone() { return telefone; }
    public Endereco getEndereco() { return endereco; }

    // Sobrescrita do método toString para fornecer uma representação em string do objeto Cliente
    @Override
    public String toString() {
        return "Cliente: " + nome + "\nCPF: " + cpf + "\nE-mail: " + email +
                "\nProfissão: " + profissao + "\nTelefone: " + telefone +
                "\nEndereço: " + endereco.toString();
    }
}

