package br.com.alura.comex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.com.alura.comex.DAO.EnderecoDao;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "endereco")
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora propriedades desconhecidas durante a desserialização
public class Endereco {

@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cep", length = 120, nullable = false)
    private String cep;

    @Column(name = "logradouro", length = 120, nullable = true)
    private String logradouro;

    @Column(name = "complemento", length = 120, nullable = true)
    private String complemento;

    @Column(name = "bairro", length = 120, nullable = true)
    private String bairro;

    @Column(name = "localidade", length = 120, nullable = true)
    private String localidade;

    @Column(name = "uf", length = 2, nullable = true)
    private String uf;

    @Column(name = "numero", length = 20, nullable = true)
    private String numero;

    public Endereco() {
        // Construtor padrão necessário para JPA
    }

    @JsonCreator
    public Endereco(@JsonProperty("cep") String cep,
                    @JsonProperty("logradouro") String logradouro,
                    @JsonProperty("complemento") String complemento,
                    @JsonProperty("bairro") String bairro,
                    @JsonProperty("localidade") String localidade,
                    @JsonProperty("uf") String uf,
                    @JsonProperty("numero") String numero) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.numero = numero;
    }

    // Getters e setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getLocalidade() { return localidade; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", cep='" + cep + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", localidade='" + localidade + '\'' +
                ", uf='" + uf + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }

    public void salvar(EnderecoDao enderecoDao) {
        enderecoDao.salvar(this);
    }

    public void atualizar(EnderecoDao enderecoDao) {
        enderecoDao.atualizar(this);
    }

    public void excluir(EnderecoDao enderecoDao) {
        enderecoDao.excluir(this);
    }
}


