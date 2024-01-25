package com.soulcode.soulib.models;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmprestimo;
    @Column(nullable = false)
    private LocalDate dataRealizada;
    @Column(nullable = false)
    private LocalDate dataDevolucao;

    private Boolean finalizado = false;

    @ManyToOne
    @JoinColumn(name = "fk_cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "fk_livro_id")
    private Livro livroEmprestado;
    
    
    public Emprestimo(){
        
    }
    
    public Integer getIdEmprestimo() {
        return idEmprestimo;
    }
    public void setIdEmprestimo(Integer idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }
    public LocalDate getDataRealizada() {
        return dataRealizada;
    }
    public void setDataRealizada(LocalDate dataRealizada) {
        this.dataRealizada = dataRealizada;
    }
    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }
    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
    public Boolean getFinalizado() {
        return finalizado;
    }
    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Livro getLivroEmprestado() {
        return livroEmprestado;
    }
    public void setLivroEmprestado(Livro livroEmprestado) {
        this.livroEmprestado = livroEmprestado;
    }
}
