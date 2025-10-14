package com.personal.task_manager.service;

import com.personal.task_manager.domain.Categoria;
import com.personal.task_manager.domain.Usuario;
import com.personal.task_manager.exceptions.NotFoundException;
import com.personal.task_manager.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria add(Categoria categoria) throws Exception {
        if (categoria.getNome().isEmpty()) {
            throw new Exception("Os campos não podem estar vazios");
        }

        return categoriaRepository.save(categoria);
    }

    public Categoria getById(Long id) {
        return categoriaRepository.findById(id).orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
    }

    public List<Categoria> listAll() {
        return categoriaRepository.findAll();
    }

    public Categoria update(Long id, String nome) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        if (categoria.isEmpty()) {
            throw new NotFoundException("Categoria não encontrada");
        }
        Categoria categoriaAtual = categoria.get();
        categoriaAtual.setNome(nome);

        return categoriaRepository.save(categoriaAtual);

    }

    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new NotFoundException("Categoria não encontrada");
        }
        categoriaRepository.deleteById(id);
    }
}
