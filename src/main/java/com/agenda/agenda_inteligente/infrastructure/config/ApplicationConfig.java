package com.agenda.agenda_inteligente.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.agenda.agenda_inteligente.application.usecase.RegistrarUsuario;
import com.agenda.agenda_inteligente.domain.repository.UsuarioRepository;

@Configuration
public class ApplicationConfig {

    @Bean
    public RegistrarUsuario registrarUsuario(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return new RegistrarUsuario(usuarioRepository, passwordEncoder);
    }
}
