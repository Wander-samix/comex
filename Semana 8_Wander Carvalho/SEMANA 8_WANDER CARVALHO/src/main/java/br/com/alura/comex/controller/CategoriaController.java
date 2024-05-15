package br.com.alura.comex.controller;

import br.com.alura.comex.dao.CategoriaDao;
import br.com.alura.comex.model.Categoria;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriaController {

    @RequestMapping("/categoria")
    public void cadastra(String nomeDaCategoria) {
        System.out.println("Estou cadastrando uma nova categoria: " + nomeDaCategoria);
        Categoria novaCategoria = new Categoria(nomeDaCategoria);
        CategoriaDao categoriaDao = new CategoriaDao();
        categoriaDao.cadastrar(novaCategoria);
    }

}