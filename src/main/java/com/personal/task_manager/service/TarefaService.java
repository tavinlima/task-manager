package com.personal.task_manager.service;

import com.personal.task_manager.domain.*;
import com.personal.task_manager.dto.tarefa.TarefaCreateDTO;
import com.personal.task_manager.dto.tarefa.TarefaUpdateDTO;
import com.personal.task_manager.enums.Status;
import com.personal.task_manager.exceptions.NotFoundException;
import com.personal.task_manager.repository.CategoriaRepository;
import com.personal.task_manager.repository.ProjetoRepository;
import com.personal.task_manager.repository.TarefaRepository;
import com.personal.task_manager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProjetoRepository projetoRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public TarefaService(TarefaRepository tarefaRepository, UsuarioRepository usuarioRepository, ProjetoRepository projetoRepository, CategoriaRepository categoriaRepository) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
        this.projetoRepository = projetoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Tarefa> listAll() {
        return tarefaRepository.findAll();
    }

    public Tarefa getById(Long id) throws NotFoundException {
        return tarefaRepository.findById(id).orElseThrow(() -> new NotFoundException("Tarefa não encontrado"));
    }

    public Tarefa add(TarefaCreateDTO tarefa) {
        Usuario usuario = usuarioRepository.findById(tarefa.getIdResponsavel()).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        Projeto projeto = projetoRepository.findById(tarefa.getIdProjeto()).orElseThrow(() -> new NotFoundException("Projeto não encontrado"));
        Categoria categoria = categoriaRepository.findById(tarefa.getIdCategoria()).orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        if (tarefa.getTitulo().isBlank() || tarefa.getDescricao().isBlank() || tarefa.getCriacao() == null || tarefa.getPrazo() == null || tarefa.getIdResponsavel() == null || tarefa.getIdCategoria() == null || tarefa.getIdProjeto() == null) {
            throw new IllegalArgumentException("Todos os campos devem ser preenchidos");
        }

        Tarefa novaTarefa = new Tarefa();
        novaTarefa.setTitulo(tarefa.getTitulo());
        novaTarefa.setDescricao(tarefa.getDescricao());
        novaTarefa.setIdResponsavel(usuario);
        novaTarefa.setIdCategoria(categoria);
        novaTarefa.setIdProjeto(projeto);
        novaTarefa.setCriacao(tarefa.getCriacao());
        novaTarefa.setPrazo(tarefa.getPrazo());

        return tarefaRepository.save(novaTarefa);
    }

    public Tarefa update(TarefaUpdateDTO tarefa) {
        Optional<Tarefa> tarefaBuscada = Optional.ofNullable(tarefaRepository.findById(tarefa.getId()).orElseThrow(() -> new NotFoundException("Tarefa não encontrada")));;

        Usuario usuario = usuarioRepository.findById(tarefa.getIdResponsavel()).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        Projeto projeto = projetoRepository.findById(tarefa.getIdProjeto()).orElseThrow(() -> new NotFoundException("Projeto não encontrado"));
        Categoria categoria = categoriaRepository.findById(tarefa.getIdCategoria()).orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        Tarefa tarefAtual = tarefaBuscada.get();
        tarefAtual.setTitulo(tarefa.getTitulo());
        tarefAtual.setDescricao(tarefa.getDescricao());
        tarefAtual.setIdResponsavel(usuario);
        tarefAtual.setIdCategoria(categoria);
        tarefAtual.setIdProjeto(projeto);
        tarefAtual.setCriacao(tarefa.getCriacao());
        tarefAtual.setPrazo(tarefa.getPrazo());
        tarefAtual.setPrioridade(tarefa.getPrioridade());
        tarefAtual.setStatus(tarefa.getStatus());
        return tarefaRepository.save(tarefAtual);

    }

    public Tarefa updateStatus(Status status, Long id) {
        Optional<Tarefa> tarefaBuscada = Optional.ofNullable(tarefaRepository.findById(id).orElseThrow(() -> new NotFoundException("Tarefa não encontrada")));;

        Tarefa tarefAtual = tarefaBuscada.get();
        tarefAtual.setStatus(status);
        return tarefaRepository.save(tarefAtual);

    }

//    public void delete(long id) throws NotFoundException {
//        if(tarefaRepository.existsById(id)) {
//            tarefaRepository.deleteById(id);
//        }
//        throw new NotFoundException("Tarefa não encontrada!");
//    }

    public void delete(long id) throws NotFoundException {

        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new NotFoundException("Tarefa não encontrado"));;

        if(tarefa != null) {
            //Ao invés de excluir a tarefa, apenas altera o status para EXCLUIDA
            //Dessa forma, mantém o histórico de tarefas criadas
            tarefa.setStatus(Status.EXCLUIDA);
            tarefaRepository.save(tarefa);
        }
    }

    public List<Tarefa> listByStatus(Status status) {
        //Criação de uma lista para armazenar apenas as tarefas de acordo com o status em que se encontra
        //A fazer, fazendo, feito
        List<Tarefa> tarefasPorStatus = new ArrayList<>();

        //Percorre toda a lista até encontrar uma tarefa com o mesmo status que foi informado no argumento da gunção
        for (Tarefa tarefa : tarefaRepository.findAll()) {
            //esse equalsIgnoreCase foi adicionado para ignorar caso o status enviado seja em CAIXA ALTA ou mínusculo
            if (tarefa.getStatus().equals(status)) {
                //E adiciona na lista criada no inicio do método
                tarefasPorStatus.add(tarefa);
            }
        }
        if (!tarefasPorStatus.isEmpty()) return tarefasPorStatus;
        throw new NotFoundException("Não há tarefas cadastradas nesse status");
    }

    public boolean searchAssociations(Long id) {
        //Função criada para procurar caso um usuário, projeto ou categoria esteja associada a alguma tarefa já criada
        //Na controller essa tarefa é chamada para impedir que um usuário, projeto ou categoria sejam excluídos caso tenha alguma tarefa associada
        for(Tarefa tarefa: tarefaRepository.findAll()) {
            if(tarefa.getIdCategoria().getIdCategoria().equals(id) || tarefa.getIdProjeto().getIdProjeto().equals(id)|| tarefa.getIdResponsavel().getIdUsuario().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
