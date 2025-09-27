package com.agenda.agenda_inteligente.domain.repository;

import java.util.List;
import java.util.Optional;

import com.agenda.agenda_inteligente.domain.model.Usuario;

public interface UsuarioRepository {
    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorEmail(String email);

    List<Usuario> listarTodos();
}
