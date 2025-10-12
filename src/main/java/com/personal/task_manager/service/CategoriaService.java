package com.personal.task_manager.service;

import com.personal.task_manager.domain.Categoria;
import com.personal.task_manager.domain.Usuario;
import com.personal.task_manager.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {
    private List<Categoria> listaCategorias = new ArrayList<Categoria>();
    private Long contadorCategorias = 3L;

    public CategoriaService() {
        Categoria c1 = new Categoria(1L, "Cotidiano");
        Categoria c2 = new Categoria(2L, "Faculdade");
        listaCategorias.add(c1);
        listaCategorias.add(c2);
    }

    public Categoria Add(String nome) {
        contadorCategorias++;
        Categoria novaCategoria = new Categoria(contadorCategorias, nome);
        listaCategorias.add(novaCategoria);

        return novaCategoria;
    }

    public Categoria GetById(Long id) {
        for (Categoria cat : listaCategorias) {
            if (cat.getId().equals(id)) return cat;
        }

        throw new NotFoundException("Categoria não encontrada");
    }

    public List<Categoria> ListAll() {
        return this.listaCategorias;
    }

    public Categoria Update(Long id, String nome) {
        for(Categoria categoria: listaCategorias) {
            if (categoria.getId().equals(id)) {
                categoria.setNome(nome);
                return categoria;
            }
        }
        throw new NotFoundException("Categoria não encontrada");
    }

    public void Delete(Long id) {
        boolean removido = listaCategorias.removeIf(cat -> cat.getId() == id);

        if(!removido) throw new NotFoundException("Categoria não encontrada");
    }
}
