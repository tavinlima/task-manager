package com.personal.task_manager.service;

import com.personal.task_manager.domain.Categoria;
import com.personal.task_manager.domain.Projeto;
import com.personal.task_manager.exceptions.NotFoundException;
import com.personal.task_manager.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjetoService {
    private final ProjetoRepository projetoRepository;

    @Autowired
    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public Projeto add(Projeto projeto) throws Exception {
        if(projeto.getNome().isEmpty() || projeto.getDescricao().isEmpty()) {
            throw new Exception("Todos os campos devem ser preenchidos!");
        }
        return projetoRepository.save(projeto);
    }

    public List<Projeto> listAll() {
        return projetoRepository.findAll();
    }

    public Projeto getById(Long id) throws NotFoundException {
        return projetoRepository.findById(id).orElseThrow(() -> new NotFoundException("Projeto não encontrado"));
    }

    public Projeto update(Long id, String nome, String descricao) {
        Optional<Projeto> projeto = projetoRepository.findById(id);

        if(projeto.isEmpty()) {
            throw new NotFoundException("Projeto não encontrado");
        }
        Projeto projetoAtual = projeto.get();
        projetoAtual.setNome(nome);
        projetoAtual.setDescricao(descricao);
        return projetoRepository.save(projetoAtual);
    }

    public void delete(Long id) {
        if(!projetoRepository.existsById(id)){

            throw new NotFoundException("Projeto não encontrado");
        }
        projetoRepository.deleteById(id);
    }
}
