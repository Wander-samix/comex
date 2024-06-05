package br.com.alura.comex.model;

public enum StatusCategoriaEnum {

    ATIVA(Boolean.TRUE),
    INATIVA(Boolean.FALSE);

    private Boolean status;

    StatusCategoriaEnum(Boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}
