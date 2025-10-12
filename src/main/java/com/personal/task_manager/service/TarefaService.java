package com.personal.task_manager.service;

import com.personal.task_manager.domain.Tarefa;
import com.personal.task_manager.enums.Prioridade;
import com.personal.task_manager.enums.Status;
import com.personal.task_manager.exceptions.NotFoundException;
import com.personal.task_manager.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TarefaService implements TarefaRepository{
    private List<Tarefa> listaTarefas = new ArrayList<>();
//    private final TarefaRepository tarefaRepository;
    private Long idTarefa = 3L;

    public TarefaService() {

        //Criação de tarefas que serão inseridas na lista de tarefas
        //Instanciando LocalDate para conseguir armazenar a criação e o prazo final para cada tarefa
        LocalDate prazo = LocalDate.of(2025, 12, 30);
        LocalDate criacao = LocalDate.of(2025, 12, 31);

        //Essas outras datas são para testar o método que verifica se existem tarefas vencidas
        LocalDate criacao2 = LocalDate.of(2023, 1, 1);
        LocalDate prazo2 = LocalDate.of(2024, 12, 30);

        //Instânciando as novas tarefas que serão adicionadas
        Tarefa t1 = new Tarefa(1L, "Ir ao mercado", "Ir ao mercado fazer a compra do mês", 1L, 1L, 1L, criacao, prazo);
        Tarefa t2 = new Tarefa(2L, "Comprar roupa na Renner", "Ir ao shopping comprar roupas na renner", 2L, 1L, 1L, criacao2, prazo2);
        Tarefa t3 = new Tarefa(3L, "Assistir a nova temporada de Gumball", "Assistir a nova temporada de O Mundo Maravilhosamente Estranho de Gumball", 1L, 1L, 1L, criacao, prazo);

        //Adicionando as tarefas instanciadas na lista de tarefas
        listaTarefas.add(t1);
        listaTarefas.add(t2);
        listaTarefas.add(t3);
    }

    public List<Tarefa> ListAll() {
        //Verificação se a lista de tarefas está vazia, caso exteja é lançada uma exceção de que não foi encontrada nenhuma tarefa na lista
        if(listaTarefas.isEmpty()) {
            throw new NotFoundException("Nenhuma tarefa encontrada");
        }
        //Retorno da lista criada
        return this.listaTarefas;
    }

    public Tarefa GetById(Long id) {
        //Percorrendo toda a lista de tarefas até que encontre uma tarefa com o mesmo id informado no argumento
        for (Tarefa tarefa : listaTarefas) {
            if (tarefa.getId().equals(id)) return tarefa;
        }
        //Validação caso nenhuma tarefa com o id informado tenha sido encontrada, com a informação de que ela pode ter sido excluída
        throw new NotFoundException("Tarefa não encontrada, ela pode ter sido excluída");
    }

    public Tarefa Add(String titulo, String descricao, Long idResponsavel, Long idCategoria, Long idProjeto, LocalDate criacao, LocalDate prazo) {
        //Verificação de que todos os campos foram informados
        if (titulo.isBlank() || descricao.isBlank() || criacao == null || prazo == null || idResponsavel == null || idCategoria == null || idProjeto == null) {
            throw new IllegalArgumentException("Todos os campos devem ser preenchidos");
        }
        //Verificação de que uma tarefa não pode ser criada caso o prazo final seja antes da data de criação
        if (prazo.isBefore(criacao)) {
            throw new IllegalArgumentException("O prazo não pode ser anterior à data de criação.");
        }

        //Contador para acrescentar os IDs as tarefas sem a necessidade de perguntar ao usuário
        idTarefa++;

        //Instância da nova tarefa que será adicionada na lista
        Tarefa novaTarefa = new Tarefa(idTarefa, titulo, descricao, idResponsavel, idCategoria, idProjeto, criacao, prazo);

        //Adição da tarefa na lista
        listaTarefas.add(novaTarefa);
        //Mensagem de retorno de que a tarefa foi adicionada a listas
        return novaTarefa;
    }

    public Tarefa Update(Long id, String titulo, String descricao, Long idResponsavel, Long idCategoria, Long idProjeto, LocalDate criacao, LocalDate prazo, Status status, Prioridade prioridade) {
        //Percorrendo toda a lista de tarefas até encontrar a tarefa com o id informado no argumento
        for(Tarefa tarefa: listaTarefas) {
            //Caso o id informado seja encontrado
            if(tarefa.getId().equals(id)) {
                //O código modifica o título para o que foi informado no argumento
                tarefa.setTitulo(titulo);
                tarefa.setDescricao(descricao);
                tarefa.setIdResponsavel(idResponsavel);
                tarefa.setIdCategoria(idCategoria);
                tarefa.setIdProjeto(idProjeto);
                tarefa.setCriacao(criacao);
                tarefa.setPrazo(prazo);
                tarefa.setStatus(status);
                tarefa.setPrioridade(prioridade);
                //E retorna a tarefa atualizada
                return tarefa;
            }
        }
        //Ou lança uma exceção caso não tenha sido encontrada
        throw new NotFoundException("Tarefa não encontrada");
    }

    public Tarefa UpdtateStatus(Long id, Status status) {
        //Esse método altera apenas o status (a fazer, fazendo feito
        //Percorrendo toda a lista de tarefas até encontrar uma tarefa com o mesmo id que foi informado no argumento
        for(Tarefa tarefa: listaTarefas) {
            if(tarefa.getId().equals(id)) {
                //E alterando caso tenha sido encontrado, com base no status enviado pelo usuário
                tarefa.setStatus(status);
                return tarefa;
            }
        }
        //Lança uma exceção caso nenhuma tarefa com aquele id tenha sido achada
        throw new NotFoundException("Tarefa não encontrada");
    }

    public void Delete(Long id) {
        //Busca de uma tarefa com o id informado pelo usuário
        Tarefa excluida = GetById(id);

        //Caso não seja encontrada, é lançada uma exceção
        if(excluida == null) {
            throw new NotFoundException("Tarefa não encontrada!");
        }
        //Caso tenha sido encontrada, a função não simplesmente excluí a tarefa, mas o status é alterado para excluído para que o usuário tenha rastreabilidade do que foi excluído
        excluida.setStatus(Status.EXCLUIDA);

    }

    public List<Tarefa> ListByStatus(String status) {
        //Criação de uma lista para armazenar apenas as tarefas de acordo com o status em que se encontra
        //A fazer, fazendo, feito
        List<Tarefa> tarefasPorStatus = new ArrayList<>();

        //Percorre toda a lista até encontrar uma tarefa com o mesmo status que foi informado no argumento da gunção
        for (Tarefa tarefa : listaTarefas) {
            //esse equalsIgnoreCase foi adicionado para ignorar caso o status enviado seja em CAIXA ALTA ou mínusculo
            if (tarefa.getStatusName().equalsIgnoreCase(status)) {
                //E adiciona na lista criada no inicio do método
                tarefasPorStatus.add(tarefa);
            }
        }
        if (!tarefasPorStatus.isEmpty()) return tarefasPorStatus;
        throw new NotFoundException("Não há tarefas cadastradas nesse status");
    }

    public boolean SearchAssociations(Long id) {
        //Função criada para procurar caso um usuário, projeto ou categoria esteja associada a alguma tarefa já criada
        //Na controller essa tarefa é chamada para impedir que um usuário, projeto ou categoria sejam excluídos caso tenha alguma tarefa associada
        for(Tarefa tarefa: listaTarefas) {
            if(tarefa.getIdCategoria().equals(id) || tarefa.getIdProjeto().equals(id)|| tarefa.getIdResponsavel().equals(id)) {
                return false;
            }
        }
        return true;
    }
}
