package com.soulcode.soulib.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.soulcode.soulib.models.Livro;
import com.soulcode.soulib.repositories.LivroRepository;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class LivroController {
    @Autowired
    private LivroRepository livroRepository;
    @GetMapping("/livros")
    public ModelAndView paginaLivro() {
        List<Livro> livros = livroRepository.findAll();
        ModelAndView mvLivros = new ModelAndView("livros");
        mvLivros.addObject("listaLivros", livros);
        return mvLivros;
    }
    @GetMapping("/livros/{idLivro}")
    public ModelAndView paginaDetalheLivro (@PathVariable Integer idLivro) {
        //@pathvariable extrai da rota o valor correspondente
        Optional<Livro> livroOpt = livroRepository.findById(idLivro);
        if (livroOpt.isPresent()) {
            Livro livro = livroOpt.get();
            ModelAndView mvLivro = new ModelAndView("livro-detalhe");
            mvLivro.addObject("livro", livro);
            return mvLivro;
        } else {
            ModelAndView mvError = new ModelAndView("erro");
            mvError.addObject("msg", "O livro não existe em nosso banco de dados.");
            return mvError;
        }
    }
    @PostMapping("/livros/delete")
    public ModelAndView deleteLivro(@RequestParam Integer idLivro) {
        ModelAndView paginaLivros = new ModelAndView("redirect:/livros");
        try {
            livroRepository.deleteById(idLivro); 
        } catch (Exception e) {
            ModelAndView mvError = new ModelAndView("erro");
            mvError.addObject("msg", "O livro que você tentou excluir está emprestado, não foi possível excluir.");
            return mvError;
        }
        return paginaLivros;               
    }
    @GetMapping("/adicionar-livro")
    public ModelAndView paginaAddLivro() {
            ModelAndView mv = new ModelAndView("adicionar-livro");
            return mv;
    }

    @PostMapping("/livros/create")
    public String createCliente(Livro livro){
        try {
            livroRepository.save(livro);
        } catch (Exception e) {
            return "erro";
        }
        return "redirect:/livros";
    }  
    @GetMapping("/livros/{idLivro}/edit")
    public ModelAndView paginaEditarLivro(@PathVariable Integer idLivro) {
        Optional<Livro> livroOpt = livroRepository.findById(idLivro);

        if (livroOpt.isPresent()){
            Livro livro = livroOpt.get();
            ModelAndView mvLivro = new ModelAndView("editar-livro");
            mvLivro.addObject("livro", livro);
            return mvLivro;
        } else{
            ModelAndView mvError = new ModelAndView("erro");
            mvError.addObject("msg", "Livro não encontrado, não é possível editar.");
            return mvError;
        }
    }
    @PostMapping("/livros/update")
    public String updateCliente(Livro livro) {
        try {
            Optional<Livro> livroOpt = livroRepository.findById(livro.getIdLivro());

            if(livroOpt.isPresent()) {
                livroRepository.save(livro);
            }
        } catch(Exception ex) {
            return "erro";
        }

        return "redirect:/livros";
    }
}
