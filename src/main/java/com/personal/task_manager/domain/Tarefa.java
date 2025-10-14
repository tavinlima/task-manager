package com.personal.task_manager.domain;

import com.personal.task_manager.enums.Prioridade;
import com.personal.task_manager.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
@Table(name = "tb_tarefas")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "idProjeto", nullable = false)
    private Projeto idProjeto;

    @ManyToOne
    @JoinColumn(name = "idResponsavel", nullable = false)
    private Usuario idResponsavel;

    @ManyToOne
    @JoinColumn(name = "idCategoria", nullable = false)
    private Categoria idCategoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade = Prioridade.BAIXA;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.A_FAZER;

    @Column(nullable = false)
    private LocalDate criacao;

    @Column(nullable = false)
    private LocalDate prazo;


}