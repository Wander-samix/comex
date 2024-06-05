package br.com.alura.comex.model;

import br.com.alura.comex.integracao.ViaCepService;
import br.com.alura.comex.integracao.ViaCepResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

import java.util.Objects;

@Embeddable
public class Endereco {

    @Column(name = "cep", length = 8, nullable = false)
    private String cep;

    @Column(name = "rua", length = 20)
    private String rua;

    @Column(name = "numero", length = 8)
    private Integer numero;

    @Column(length = 100)
    private String complemento;

    @Column(length = 80)
    private String bairro;

    @Column(length = 50)
    private String cidade;

    @Column(length = 20)
    private String estado;

    @Transient
    private ViaCepService viaCepService = new ViaCepService(); // Instância de serviço para integração com ViaCEP

    public Endereco(String cep) {
        this.cep = cep;
        buscaEnderecoPorCep(cep);
    }

    public Endereco(String rua, Integer numero, String bairro, String cidade, String estado) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Endereco() {
    }

    public void buscaEnderecoPorCep(String cep) {
        // Cria objeto que integra com ViaCEP
        ViaCepResponse retorno = viaCepService.buscaPorCep(cep);
        if (retorno != null) {
            this.preencheEnderecoCompleto(retorno);
        }
    }

    public void preencheEnderecoCompleto(ViaCepResponse model) {
        this.rua = model.getLogradouro();
        this.complemento = model.getComplemento();
        this.bairro = model.getBairro();
        this.cidade = model.getLocalidade();
        this.estado = model.getUf();
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "cep='" + cep + '\'' +
                ", rua='" + rua + '\'' +
                ", numero=" + numero +
                ", complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(cep, endereco.cep) &&
                Objects.equals(rua, endereco.rua) &&
                Objects.equals(numero, endereco.numero) &&
                Objects.equals(complemento, endereco.complemento) &&
                Objects.equals(bairro, endereco.bairro) &&
                Objects.equals(cidade, endereco.cidade) &&
                Objects.equals(estado, endereco.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cep, rua, numero, complemento, bairro, cidade, estado);
    }

    // Getters e setters
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
        buscaEnderecoPorCep(cep);
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
