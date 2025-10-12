package com.personal.task_manager.enums;

public enum Status {
    A_FAZER("A fazer"),
    FAZENDO("Fazendo"),
    FEITO("Feito"),
    EXCLUIDA("Excluída");

    private String status;

    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
