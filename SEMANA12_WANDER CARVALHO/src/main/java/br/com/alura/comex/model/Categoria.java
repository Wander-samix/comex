package br.com.alura.comex.model;

import jakarta.persistence.*;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCategoriaEnum status;

    // Construtores, getters e setters

    public Categoria() {
    }

    public Categoria(String nome, StatusCategoriaEnum status) {
        this.nome = nome;
        this.status = status;
    }

    public Categoria(String nome) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public StatusCategoriaEnum getStatus() {
        return status;
    }

    public void setStatus(StatusCategoriaEnum status) {
        this.status = status;
    }

    public void toggleStatus() {
        if (this.status == StatusCategoriaEnum.ATIVA) {
            this.status = StatusCategoriaEnum.INATIVA;
        } else {
            this.status = StatusCategoriaEnum.ATIVA;
        }
    }
}
