package com.soulcode.soulib.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Em entidades, não usamos os tipos primitivos:
// WRAPPER CLASS 
// -> int -> Integer
// -> double -> Double
// -> boolean -> Boolean
// -> char -> Char
// -> long -> Long
@Table(name = "clientes")
@Entity
public class Cliente {
    // GeneratedValue determina a estrategia de para gerar um valor para a tabela
    // Identity = Auto_Increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    // nullable = false equivale a not null
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 42, unique = true)
    private String telefone;

    @Column(length = 120, unique = true)
    private String email;

    public Cliente(){
        // caso você use mais de um construtor, coloque o construtor vazio.
    }

    public Integer getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
}
