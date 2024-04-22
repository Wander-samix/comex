package br.com.alura.comex.testes;

import br.com.alura.comex.endereco.ConsultaCep;
import br.com.alura.comex.endereco.Endereco;

public class TesteCep {
    public static void main(String[] args) {
        ConsultaCep service = new ConsultaCep();
        Endereco endereco = service.consultaCep("12237600");
        if (endereco != null) {
            System.out.println(endereco);
        }
    }
}

