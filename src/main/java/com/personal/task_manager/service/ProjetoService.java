package com.personal.task_manager.service;

import com.personal.task_manager.domain.Categoria;
import com.personal.task_manager.domain.Projeto;
import com.personal.task_manager.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProjetoService {
    private List<Projeto> listaProjetos = new ArrayList<>();
    private Long contadorProjetos = 2L;

    public ProjetoService() {
        Projeto p1 = new Projeto(1L, "Organização pessoal", "Projeto criado para me ajudar na organização das tarefas de casa");
        listaProjetos.add(p1);
    }

    public Projeto Add(String nome, String descricao) {
        Projeto novoProjeto = new Projeto(contadorProjetos, nome, descricao);
        listaProjetos.add(novoProjeto);

        return novoProjeto;
    }

    public List<Projeto> ListAll() {
        return listaProjetos;
    }

    public Projeto GetById(Long id) {
        for (Projeto p : listaProjetos) {
            if (p.getId().equals(id)) return p;
        }

        throw new NotFoundException("Projeto não encontrado");
    }

    public Projeto Update(Long id, String nome, String descricao) {
        for (Projeto p : listaProjetos) {
            if (p.getId().equals(id)) {
                p.setNome(nome);
                p.setDescricao(descricao);
                return p;
            }
        }
        throw new NotFoundException("Projeto não encontrado");
    }

    public void Delete(Long id) {
        boolean removido = listaProjetos.removeIf(p -> p.getId().equals(id));

        if (!removido) throw new NotFoundException("Projeto não encontrado");
    }
}
