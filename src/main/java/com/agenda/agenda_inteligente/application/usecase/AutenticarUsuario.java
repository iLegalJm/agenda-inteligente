package com.agenda.agenda_inteligente.application.usecase;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agenda.agenda_inteligente.domain.model.Usuario;
import com.agenda.agenda_inteligente.domain.repository.UsuarioRepository;

@Service
public class AutenticarUsuario {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AutenticarUsuario(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Usuario> ejecutar(String email, String password) {
        return usuarioRepository.buscarPorEmail(email)
                .filter(usuario -> passwordEncoder.matches(password, usuario.getPassword()));
    }
}
