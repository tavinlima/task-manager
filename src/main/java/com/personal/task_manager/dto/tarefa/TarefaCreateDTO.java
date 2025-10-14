package com.personal.task_manager.dto.tarefa;

import com.personal.task_manager.enums.Prioridade;
import com.personal.task_manager.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TarefaCreateDTO {
    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @NotNull
    private Long idProjeto;

    @NotNull
    private Long idResponsavel;

    @NotNull
    private Long idCategoria;

    @NotBlank
    private Prioridade prioridade;

    @NotBlank
    private Status status = Status.A_FAZER;

    @NotNull
    private LocalDate criacao;

    @NotNull
    private LocalDate prazo;
}
