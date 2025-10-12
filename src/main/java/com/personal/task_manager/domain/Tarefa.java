package com.personal.task_manager.domain;

import java.time.LocalDate;

public class Tarefa {
    private enum Prioridade {
        BAIXA, MEDIA, ALTA;
    }

    private enum Status {
        A_FAZER,FAZENDO,FEITO;
    }
    private Long id;
    private String titulo;
    private String descricao;
    private Long idProjeto;
    private Long idResponsavel;
    private Long idCategoria;

    private Prioridade prioridade ;
    private Status status;
    private LocalDate criacao;
    private LocalDate prazo;

    public Tarefa(Long idTarefa, String titulo, String descricao, Long idResponsavel, Long idCategoria, Long idProjeto, LocalDate criacao, LocalDate prazo) {
        this.id = idTarefa;
        this.titulo =titulo;
        this.descricao =descricao;
        this.idResponsavel =idResponsavel;
        this.idCategoria =idCategoria;
        this.idProjeto =idProjeto;
        this.prioridade =Prioridade.BAIXA;
        this.status=Status.A_FAZER;
        this.criacao = LocalDate.now();
        this.prazo = LocalDate.now();
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Long idProjeto) {
        this.idProjeto = idProjeto;
    }

    public Long getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Long idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Status getStatus() {
        return status;
    }

    public String getStatusName() {
        return status.name();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCriacao() {
        return criacao;
    }

    public void setCriacao(LocalDate criacao) {
        this.criacao = criacao;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "prazo=" + prazo +
                ", criacao=" + criacao +
                ", status=" + status +
                ", prioridade=" + prioridade +
                ", idCategoria=" + idCategoria +
                ", idResponsavel=" + idResponsavel +
                ", idProjeto=" + idProjeto +
                ", descricao='" + descricao + '\'' +
                ", titulo='" + titulo + '\'' +
                ", id=" + id +
                '}';
    }
}
