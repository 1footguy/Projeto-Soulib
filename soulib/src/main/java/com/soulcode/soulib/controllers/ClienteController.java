package com.soulcode.soulib.controllers;
// get - post - redirect

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.soulcode.soulib.models.Cliente;
import com.soulcode.soulib.repositories.ClienteRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;




// classes como @controller ou @repository não precisam instanciar (new)
// pois é feito automaticamente
// injeção de dependencia
@Controller
public class ClienteController {   
    @Autowired
    private ClienteRepository clienteRepository;
    @GetMapping("/clientes")
    public ModelAndView paginaCliente(){
        List<Cliente> clientes = clienteRepository.findAll(); // equivale ao select * from clientes
        //ModelAndView é utilizado quando precisamos fornecer dados para o html
        ModelAndView mv = new ModelAndView("clientes"); // indica qual o template/view
        mv.addObject("listaCliente", clientes);
        return mv;
        // return "cliente"; //arquivo.html da pasta template, só funciona para paginas simples, precisa ser tipo String
     // Template Engine (thymeleaf) = recurso para gerar as páginas
    // HTML dinamicamente usando o back-end.
    }
    // Parametros da Rota: /clientes/numID
    @GetMapping("/clientes/{id}")
    public ModelAndView paginaDetalheCliente (@PathVariable Integer id) {
        //@pathvariable extrai da rota o valor correspondente
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            ModelAndView mv = new ModelAndView("cliente-detalhe");
            mv.addObject("cliente", cliente);
            return mv;
        } else {
            ModelAndView mvError = new ModelAndView("erro");
            mvError.addObject("msg", "O cliente não existe em nosso banco de dados.");
            return mvError;
        }
    }
    @PostMapping("/clientes/delete")
    public ModelAndView deleteCliente(@RequestParam Integer id) {
        // RequestParam no Post vai procurar valor com o nome declarado
        ModelAndView paginaCliente = new ModelAndView("redirect:/clientes");
        try {
            clienteRepository.deleteById(id);
        } catch (Exception e) {
            ModelAndView mvError = new ModelAndView("erro");
            mvError.addObject("msg", "O cliente que você tentou excluir está com um livro emprestado, não foi possível excluir.");
            return mvError;
        }
        //redireciona o usuario para lista de clientes(clientes.html) após deletar com sucesso.
        return paginaCliente;
    }
    @PostMapping("clientes/create")
    public String createCliente( Cliente cliente){
        try {
            clienteRepository.save(cliente);
        } catch (Exception e) {
            return "erro";
        }
        return "redirect:/clientes";
    }  
    @GetMapping("/clientes/{id}/edit")
    public ModelAndView paginaEditarCliente(@PathVariable Integer id) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);

        if (clienteOpt.isPresent()){
            Cliente cliente = clienteOpt.get();
            ModelAndView mv = new ModelAndView("editar-cliente");
            mv.addObject("cliente", cliente);
            return mv;
        } else{
            ModelAndView mvError = new ModelAndView("erro");
            mvError.addObject("msg", "Cliente não encontrado, impossivel editar.");
            return mvError;
        }
    }

    @PostMapping("/clientes/update")
    public String updateCliente(Cliente cliente) {
        // Na ação de atualizar, o ID do cliente atual será enviado junto.
        try {
            Optional<Cliente> clienteOpt = clienteRepository.findById(cliente.getIdCliente());

            if(clienteOpt.isPresent()) {
                // Antes de efetuar a operação, será checado o campo ID.
                // Se houver um valor, será executado update, se não houver
                // será executado um create.
                clienteRepository.save(cliente);
            }
        } catch(Exception ex) {
            return "erro";
        }
        return "redirect:/clientes";
    }
       
}
