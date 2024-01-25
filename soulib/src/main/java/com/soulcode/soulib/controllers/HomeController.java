package com.soulcode.soulib.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Configura nossa classe como um controlador web.
@Controller
public class HomeController {
    
    // Configura o metodo para responder uma requisição para /home
    // Dentro do método, vem a lógica do controlador
    // Exibir páginas, buscar dados, validar dados.
    
    @GetMapping({"/home", "/", "/h"})
    public String paginaHome(){
        return "index";
    }
    @GetMapping("/contatos")
    public String paginaContato(){
        return "contatos";
    }
}
