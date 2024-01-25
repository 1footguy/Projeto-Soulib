package com.soulcode.soulib.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.soulcode.soulib.models.Cliente;
import com.soulcode.soulib.models.Emprestimo;
import com.soulcode.soulib.models.Livro;
import com.soulcode.soulib.repositories.ClienteRepository;
import com.soulcode.soulib.repositories.EmprestimoRepository;
import com.soulcode.soulib.repositories.LivroRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller // expor os mapeamentos
public class EmprestimoController {
    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LivroRepository livroRepository;

    @GetMapping("/emprestimos")
    public ModelAndView paginaEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();

        ModelAndView mv = new ModelAndView("emprestimos");
        mv.addObject("listaEmprestimos", emprestimos);

        return mv;
    }
    @GetMapping("/novo-emprestimo")
    public ModelAndView paginaNovoEmprestimo() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<Livro> livros = livroRepository.findAll();
        ModelAndView paginaNovoEmprestimo =  new ModelAndView("novo-emprestimo");
        paginaNovoEmprestimo.addObject("listaClientes", clientes);
        paginaNovoEmprestimo.addObject("listaLivros", livros);
        return paginaNovoEmprestimo;
    }
    
    @PostMapping("/emprestimos/create")
    public String createEmprestimo(@RequestParam Integer idCliente, @RequestParam Integer idLivro, Emprestimo emprestimo) {
        try {
            Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
            Optional<Livro> livroOpt = livroRepository.findById(idLivro);

            // Para inserir o empréstimo, cliente e livro devem existir
            if(clienteOpt.isPresent() && livroOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                Livro livro = livroOpt.get();
                
                // Associamos o cliente ao empréstimo
                emprestimo.setCliente(cliente);

                // Associamos o livro ao empréstimo
                emprestimo.setLivroEmprestado(livro);

                emprestimoRepository.save(emprestimo);
            }
        } catch(Exception ex) {
            return "erro";
        }
        return "redirect:/emprestimos";
    }
    @GetMapping("/emprestimos/{idEmprestimo}/edit")
    public ModelAndView paginaEditarEmprestimo(@PathVariable Integer idEmprestimo) {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(idEmprestimo);

        if (emprestimoOpt.isPresent()) {
            Emprestimo emprestimo = emprestimoOpt.get();
            ModelAndView mvEmp = new ModelAndView("editar-emprestimo");
            mvEmp.addObject("emprestimo", emprestimo);
            return mvEmp;
        } else {
            ModelAndView mvError = new ModelAndView("erro");
            mvError.addObject("msg", "Esse emprestimo não existe");
            return mvError;          
        }
    }
    @PostMapping("/emprestimos/update")
    public String updateEmprestimo(@RequestParam Integer idCliente, @RequestParam Integer idLivro, Emprestimo emprestimo) {
        try {
            Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(emprestimo.getIdEmprestimo());
            Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
            Optional<Livro> livroOpt = livroRepository.findById(idLivro);

            if(emprestimoOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                Livro livro = livroOpt.get();
                emprestimo.setCliente(cliente);
                emprestimo.setLivroEmprestado(livro);
                emprestimoRepository.save(emprestimo);
            }
        } catch(Exception ex) {
            return "erro";
        }
        return "redirect:/emprestimos";
    }
    @PostMapping("/emprestimos/delete")
    public ModelAndView deleteEmprestimo(@RequestParam Boolean emprestimoFim, @RequestParam Integer idCliente, @RequestParam Integer idLivro, Emprestimo emprestimo) {
        ModelAndView mvEmp = new ModelAndView("redirect:/emprestimos");
        try {
            Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
            Optional<Livro> livroOpt = livroRepository.findById(idLivro);
            Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(emprestimo.getIdEmprestimo());
            if (emprestimoOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                Livro livro = livroOpt.get();
                emprestimo.setCliente(cliente);
                emprestimo.setLivroEmprestado(livro);
                if (emprestimoFim) {
                    emprestimoRepository.deleteById(emprestimo.getIdEmprestimo());
                    return mvEmp;
                }else{
                    ModelAndView mvError2 = new ModelAndView("erro");
                    mvError2.addObject("msg", "Não é possivel excluir empréstimos não finalizado. Caso ja tenha sido finalizado, edite e tente novamente");
                    return mvError2;
                }
            }
        } catch (Exception e) {
            ModelAndView mvError = new ModelAndView("erro");
            mvError.addObject("msg", "Este emprestimo não foi encontrado em nosso banco de dados.");
            return mvError;
        }
        return mvEmp;
    }
}
