package com.soulcode.soulib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.soulcode.soulib.models.Livro;

// nomeando a interface
// nome da entidade + repository

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {
    // Um repository simplifica a interação com o banco de dados. Disponibilizando
    // uma interface que possui métodos básicos como listar, salvar e deletar.
}
