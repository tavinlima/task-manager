package com.personal.task_manager.service;

import com.personal.task_manager.domain.Tarefa;
import com.personal.task_manager.domain.Usuario;
import com.personal.task_manager.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TarefaService {
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Long idTarefa = 3L;

    public TarefaService() {
        DateFormat f = DateFormat.getDateInstance();
        LocalDate prazo = LocalDate.of(2025, 12, 30);
        LocalDate criacao = LocalDate.of(2025, 12, 31);

        Tarefa t1 = new Tarefa(1L, "Ir ao mercado", "Ir ao mercado fazer a compra do mês", 1L, 1L, 1L, criacao, prazo);
        Tarefa t2 = new Tarefa(2L, "Comprar roupa na Renner", "Ir ao shopping comprar roupas na renner", 1L, 1L, 1L, criacao, prazo);
        Tarefa t3 = new Tarefa(3L, "Assistir a nova temporada de Gumball", "Assistir a nova temporada de O Mundo Maravilhosamente Estranho de Gumball", 1L, 1L, 1L, criacao, prazo);

        listaTarefas.add(t1);
        listaTarefas.add(t2);
        listaTarefas.add(t3);
    }

    public List<Tarefa> ListAll() {
        return this.listaTarefas;
    }

    public Tarefa GetById(int id) {
        for (Tarefa tarefa : listaTarefas) {
            if (tarefa.getId() == id) return tarefa;
        }
        throw new NotFoundException("Tarefa não encontrada");
    }

    public String Add(String titulo, String descricao, Long idResponsavel, Long idCategoria, Long idProjeto, LocalDate criacao, LocalDate prazo) {
        if (titulo.isBlank() || descricao.isBlank() || criacao == null || prazo == null || idResponsavel == null || idCategoria == null || idProjeto == null) {
            throw new IllegalArgumentException("Todos os campos devem ser preenchidos");
        }

        if (prazo.isBefore(criacao)) {
            throw new IllegalArgumentException("O prazo não pode ser anterior à data de criação.");
        }

        idTarefa++;

        Tarefa novaTarefa = new Tarefa(idTarefa, titulo, descricao, idResponsavel, idCategoria, idProjeto, criacao, prazo);

        listaTarefas.add(novaTarefa);
        return String.format("A tarefa '%s' foi adicionada com sucesso ao projeto %d!", titulo, idProjeto);
    }

    public Tarefa Update(Long id, String titulo, String descricao, Long idResponsavel, Long idCategoria, Long idProjeto, LocalDate criacao, LocalDate prazo) {
        for(Tarefa tarefa: listaTarefas) {
            if(tarefa.getId().equals(id)) {
                tarefa.setTitulo(titulo);
                tarefa.setDescricao(descricao);
                tarefa.setIdResponsavel(idResponsavel);
                tarefa.setIdCategoria(idCategoria);
                tarefa.setIdProjeto(idProjeto);
                tarefa.setCriacao(criacao);
                tarefa.setPrazo(prazo);
//                tarefa.setPrioridade("ALTA");
            }
        }
        return null;
    }

    public void Delete(int id) {
        boolean removido = listaTarefas.removeIf(tarefa -> tarefa.getId() == id);
        if (!removido) throw new NotFoundException("Tarefa não encontrada!");
    }

    public List<Tarefa> ListByStatus(String status) {
        List<Tarefa> tarefasPorStatus = new ArrayList<>();

        for (Tarefa tarefa : listaTarefas) {
            if (tarefa.getStatusName().equalsIgnoreCase(status)) {
                tarefasPorStatus.add(tarefa);
            }
        }
        if (!tarefasPorStatus.isEmpty()) return tarefasPorStatus;
        throw new NotFoundException("Não há tarefas cadastradas nesse status");
    }

    public boolean SearchAssociations(Long id) {
        for(Tarefa tarefa: listaTarefas) {
            if(tarefa.getIdCategoria().equals(id) || tarefa.getIdProjeto().equals(id)|| tarefa.getIdResponsavel().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
