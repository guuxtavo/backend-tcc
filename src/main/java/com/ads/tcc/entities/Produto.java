package com.ads.tcc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="tb_produto")
public class Produto implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @NotBlank(message = "O campo 'modelo' não pode ser vazio!")
    @Column(nullable = false)
    private String modelo;

    @NotBlank(message = "O campo 'cor' não pode ser vazio!")
    @Column(nullable = false)
    private String cor;

    @Column(nullable = false, columnDefinition = "DOUBLE")
    private Double pontuacao;

    @NotBlank(message = "O campo 'tipo' não pode ser vazio!")
    @Column(nullable = false)
    private String tipo;

    @NotBlank(message = "O campo 'status' não pode ser vazio")
    private String status;

    @JsonIgnore
    @ManyToMany(mappedBy = "produtos")
    private List<Producao> producoes;

    public Produto(){

    }

    public Produto(Long id, String modelo, String cor, Double pontuacao, String tipo, List<Producao> producoes, String status) {
        this.id = id;
        this.modelo = modelo;
        this.cor = cor;
        this.pontuacao = pontuacao;
        this.tipo = tipo;
        this.producoes = producoes;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Producao> getProducoes() {
        return producoes;
    }

    public void setProducoes(List<Producao> producoes) {
        this.producoes = producoes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
