package com.agenda.agenda_inteligente.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com.agenda.agenda_inteligente.domain.model.Usuario;
import com.agenda.agenda_inteligente.domain.repository.UsuarioRepository;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final JpaUsuarioRepository jpaRepository;

    public UsuarioRepositoryAdapter(JpaUsuarioRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        JpaUsuarioEntity entity = new JpaUsuarioEntity();
        entity.setNombre(usuario.getNombre());
        entity.setEmail(usuario.getEmail());
        entity.setPassword(usuario.getPassword());
        entity.setRol(usuario.getRol());
        JpaUsuarioEntity saved = jpaRepository.save(entity);
        return new Usuario(saved.getId(), saved.getNombre(), saved.getEmail(), saved.getPassword(), saved.getRol());
    }

    @Override
    public java.util.Optional<Usuario> buscarPorEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(entity -> new Usuario(entity.getId(), entity.getNombre(), entity.getEmail(), entity.getPassword(),
                        entity.getRol()));
    }

    @Override
    public java.util.List<Usuario> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(entity -> new Usuario(entity.getId(), entity.getNombre(), entity.getEmail(), entity.getPassword(),
                        entity.getRol()))
                .toList();
    }

}
