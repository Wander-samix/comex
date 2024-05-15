package br.com.alura.comex.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BemVindoController {
    @RequestMapping ("/api/bemvindo")
    public String bemvindo(){
        return "Bem-vindo ao sistema Comex";
    }

}
