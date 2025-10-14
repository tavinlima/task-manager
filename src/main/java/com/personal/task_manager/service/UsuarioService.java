package com.personal.task_manager.service;

import com.personal.task_manager.domain.Usuario;
import com.personal.task_manager.exceptions.NotFoundException;
import com.personal.task_manager.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listAll() {
        return usuarioRepository.findAll();
    }

    public Usuario getById(Long id) throws NotFoundException {
        return usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    public Usuario add(Usuario usuario) throws Exception {
        if(usuario.getNome().isEmpty() || usuario.getEmail().isEmpty()) {
            throw new Exception("Os campos não podem estar vazios");
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, String nome, String email) {
        Optional<Usuario> usuarioBuscado = usuarioRepository.findById(id);

        if(usuarioBuscado.isEmpty()) {
            throw new NotFoundException ("Usuário não encontrado");
        }
        Usuario usuarioAtual = usuarioBuscado.get();
        usuarioAtual.setNome(nome);
        usuarioAtual.setEmail(email);
        return usuarioRepository.save(usuarioAtual);

    }

    public void delete(Long id) {
        if(!usuarioRepository.existsById(id)) {
            throw new NotFoundException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }


}
