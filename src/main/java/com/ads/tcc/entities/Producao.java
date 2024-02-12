package com.ads.tcc.entities;

import com.ads.tcc.entities.user.Funcionario;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_producao")
public class Producao implements Serializable {

    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data;

    @Column(columnDefinition = "DOUBLE", nullable = false)
    private Double pontuacao;

    @Column(columnDefinition = "DOUBLE", nullable = false)
    private Double valorComissao;

    @Column(columnDefinition = "VARCHAR(400)")
    private String observacao;

    private String status;

    @ManyToMany
    @JoinTable(name = "tb_produtos_por_producao",
            joinColumns = @JoinColumn(name = "producao_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produtos;

    @ManyToMany
    @JoinTable(name = "tb_funcionarios_por_producao",
            joinColumns = @JoinColumn(name = "producao_id"),
            inverseJoinColumns = @JoinColumn(name = "funcionario_id"))
    private List<Funcionario> funcionarios;

    public Producao() {

    }

    public Producao(Long id, LocalDate data, Double pontuacao, Double valorComissao, String status,  String observacao ,List<Produto> produtos, List<Funcionario> funcionarios) {
        this.id = id;
        this.data = data;
        this.pontuacao = pontuacao;
        this.valorComissao = valorComissao;
        this.status = status;
        this.observacao = observacao;
        this.produtos = produtos;
        this.funcionarios = funcionarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Double getValorComissao() {
        return valorComissao;
    }

    public void setValorComissao(Double valorComissao) {
        this.valorComissao = valorComissao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Funcionario> getFuncionarios(){
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios){
        this.funcionarios = funcionarios;
    }

}
