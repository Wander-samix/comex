package br.com.alura.comex.dto;

public class ClienteDTO {

    private String nome;
    private String cpf;
    private String telefone;
    private String local;

    public ClienteDTO(String nome, String cpf, String telefone, String local) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.local = local;
    }

    // Getters e setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getRua() {
        return "";
    }

    public Integer getNumero() {
        return 0;
    }

    public String getComplemento() {
        return "";
    }

    public String getBairro() {
        return "";
    }

    public String getCidade() {
        return "";
    }

    public String getEstado() {
        return "";
    }

    public String getEmail() {
        return "";
    }
}
