package com.agenda.agenda_inteligente.application.service;

import java.time.Instant;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agenda.agenda_inteligente.application.ports.input.AuthServicePort;
import com.agenda.agenda_inteligente.application.ports.output.RefreshTokenPersistencePort;
import com.agenda.agenda_inteligente.application.ports.output.UserPersistencePort;
import com.agenda.agenda_inteligente.domain.exceptions.UserDuplicatedException;
import com.agenda.agenda_inteligente.domain.exceptions.UserNotFoundException;
import com.agenda.agenda_inteligente.domain.model.RefreshToken;
import com.agenda.agenda_inteligente.domain.model.User;
import com.agenda.agenda_inteligente.infrastructure.security.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServicePort {
    private final UserPersistencePort usuarioPersistencePort;
    private final RefreshTokenPersistencePort refreshTokenPersistencePort;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtService;

    @Override
    public Map<String, String> login(String email, String password) {
        User usuario = usuarioPersistencePort.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElseThrow(() -> new UserNotFoundException(
                        password != null ? "Credenciales inválidas" : "Usuario no encontrado"));

        String accessToken = jwtService.generarToken(usuario.getEmail(), usuario.getRol().name(), 15 * 60 * 1000); // 15
                                                                                                                   // minutos
        String refreshTokenStr = jwtService.generarToken(usuario.getEmail(), usuario.getRol().name(),
                7 * 24 * 60 * 60 * 1000); // 7 días

        RefreshToken refreshToken = RefreshToken.builder()
                .token(refreshTokenStr)
                .userId(usuario.getId())
                .expiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60))
                .revoked(false)
                .build();

        refreshTokenPersistencePort.save(refreshToken);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshTokenStr);
    }

    @Override
    public User register(User user) {
        if (usuarioPersistencePort.findByEmail(user.getEmail()).isPresent()) {
            throw new UserDuplicatedException("El email " + user.getEmail() + " ya está registrado");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usuarioPersistencePort.save(user);
    }

    @Override
    public Map<String, String> refreshToken(String refreshTokenStr) {
        RefreshToken token = refreshTokenPersistencePort.findByToken(refreshTokenStr)
                .filter(rt -> !rt.isRevoked() && rt.getExpiryDate().isAfter(Instant.now()))
                .orElseThrow(() -> new RuntimeException("Refresh token inválido o expirado"));

        String email = jwtService.getEmail(refreshTokenStr);
        String rol = jwtService.getRol(refreshTokenStr);

        String newAccessToken = jwtService.generarToken(email, rol, 15 * 60 * 1000); // 15 minutos

        return Map.of("accessToken", newAccessToken);
    }

    @Override
    public void logout(String refreshToken) {
        refreshTokenPersistencePort.revoke(refreshToken);
    }
}
