package com.personal.task_manager.service;

import com.personal.task_manager.domain.Usuario;
import com.personal.task_manager.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {
    private List<Usuario> listaUsuarios = new ArrayList<Usuario>();
    private Long contadorUsuarios = 2l;

    public UsuarioService() {
        Usuario novoUsuario = new Usuario(0l, "Dan Reynolds", "dan@email.com");
        Usuario novoUsuario2 = new Usuario(1l, "Taylor Swift", "taylor@email.com");
        Usuario novoUsuario3 = new Usuario(2l, "Odete Roitman", "odete@email.com");

        listaUsuarios.add(novoUsuario);
        listaUsuarios.add(novoUsuario2);
        listaUsuarios.add(novoUsuario3);
    }

    public List<Usuario> ListAll() {
        return this.listaUsuarios;
    }

    public Usuario GetById(long id) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getId().equals(id)) return usuario;
        }
        throw new NotFoundException("Usuário não encontrado");
    }

    public Usuario Add(String nome, String email) {
        contadorUsuarios++;
        Usuario novoUsuario = new Usuario(contadorUsuarios, nome, email);
        listaUsuarios.add(novoUsuario);
        return novoUsuario;
    }

    public Usuario Update(Long id, String nome, String email) {
        for(Usuario usuario: listaUsuarios) {
            if (usuario.getId().equals(id)) {
                usuario.setNome(nome);
                usuario.setEmail(email);
                return usuario;
            }
        }
        throw new NotFoundException("Usuário não encontrado");
    }

    public void Delete(long id) {
        boolean removido = this.listaUsuarios.removeIf(user -> user.getId() == id);
        if (!removido) {
            throw new NotFoundException("Usuário não encontrado");
        }
    }
}
