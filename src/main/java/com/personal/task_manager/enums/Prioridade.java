package com.personal.task_manager.enums;

public enum Prioridade {
    BAIXA("Baixa"),
    MEDIA("Média"),
    ALTA("Alta");

    private String prioridade;

    Prioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    @Override
    public String toString() {
        return prioridade;
    }
}