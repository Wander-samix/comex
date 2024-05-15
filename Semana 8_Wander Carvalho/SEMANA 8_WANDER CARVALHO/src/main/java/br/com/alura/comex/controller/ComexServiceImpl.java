package br.com.alura.comex.controller;

import javax.persistence.EntityManager;

public class ComexServiceImpl extends ComexService {
    public ComexServiceImpl(EntityManager em) {
        super(em);
    }
}
