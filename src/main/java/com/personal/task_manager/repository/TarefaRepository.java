package com.personal.task_manager.repository;

import com.personal.task_manager.domain.Tarefa;
import com.personal.task_manager.enums.Prioridade;
import com.personal.task_manager.enums.Status;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TarefaRepository {
    List<Tarefa> ListAll();
    Tarefa GetById(Long id);
    Tarefa Add(String titulo, String descricao, Long idResponsavel, Long idCategoria, Long idProjeto, LocalDate criacao, LocalDate prazo);
    Tarefa Update(Long id, String titulo, String descricao, Long idResponsavel, Long idCategoria, Long idProjeto, LocalDate criacao, LocalDate prazo, Status status, Prioridade prioridade);
    void Delete(Long id);
    List<Tarefa> ListByStatus(String status);
    boolean SearchAssociations(Long id);

}
