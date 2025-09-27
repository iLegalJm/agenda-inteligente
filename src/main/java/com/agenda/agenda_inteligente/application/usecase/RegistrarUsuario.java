package com.agenda.agenda_inteligente.application.usecase;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.agenda.agenda_inteligente.domain.model.Rol;
import com.agenda.agenda_inteligente.domain.model.Usuario;
import com.agenda.agenda_inteligente.domain.repository.UsuarioRepository;

public class RegistrarUsuario {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrarUsuario(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario ejecutar(String nombre, String email, String password, String rol) {
        // Validar si ya existe el usuario con ese email
        if (usuarioRepository.buscarPorEmail(email).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        // Crear y guardar el nuevo usuario
        Usuario nuevoUsuario = new Usuario(
                null,
                nombre,
                email,
                passwordEncoder.encode(password),
                Rol.valueOf(rol.toUpperCase()));

        return usuarioRepository.guardar(nuevoUsuario);
    }

}
