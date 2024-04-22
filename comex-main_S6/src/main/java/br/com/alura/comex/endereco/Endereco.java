package br.com.alura.comex.endereco;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos não mapeados no JSON
public class Endereco {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge; // Campo adicionado
    private String gia; // Campo adicionado
    private String ddd; // Campo adicionado
    private String siafi; // Campo adicionado
    private String numero;

    // Construtor vazio necessário para a deserialização quando não são utilizadas anotações nos parâmetros do construtor
    public Endereco() {
    }

    // Construtor com anotações JsonProperty para mapeamento direto de JSON para os parâmetros do construtor
    @JsonCreator
    public Endereco(@JsonProperty("cep") String cep,
                    @JsonProperty("logradouro") String logradouro,
                    @JsonProperty("complemento") String complemento,
                    @JsonProperty("bairro") String bairro,
                    @JsonProperty("localidade") String localidade,
                    @JsonProperty("uf") String uf) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
    }

    // Getters e setters para cada campo
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getLocalidade() { return localidade; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    // Representação do objeto em String para fácil visualização
    @Override
    public String toString() {
        return "Endereco{" +
                "cep='" + cep + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", numero='" + numero + '\'' +
                ", complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", localidade='" + localidade + '\'' +
                ", uf='" + uf + '\'' +
                '}';
    }

    // Exemplo de configuração do ObjectMapper para ser usado ao deserializar
//    public static void configureObjectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


