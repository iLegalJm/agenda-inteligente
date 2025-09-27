package com.agenda.agenda_inteligente.interfaces.rest;

import com.agenda.agenda_inteligente.application.usecase.AutenticarUsuario;
import com.agenda.agenda_inteligente.application.usecase.RegistrarUsuario;
import com.agenda.agenda_inteligente.domain.model.Usuario;
import com.agenda.agenda_inteligente.infrastructure.security.JwtProvider;
import com.agenda.agenda_inteligente.interfaces.rest.dto.ApiResponse;
import com.agenda.agenda_inteligente.interfaces.rest.dto.RegisterRequest;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AutenticarUsuario autenticarUsuario;
    private final RegistrarUsuario registrarUsuario;
    private final JwtProvider jwtProvider;

    public AuthController(AutenticarUsuario autenticarUsuario, RegistrarUsuario registrarUsuario,
            JwtProvider jwtProvider) {
        this.autenticarUsuario = autenticarUsuario;
        this.registrarUsuario = registrarUsuario;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Usuario>> register(@Valid @RequestBody RegisterRequest request) {
        try {
            Usuario guardado = registrarUsuario.ejecutar(
                    request.getNombre(),
                    request.getEmail(),
                    request.getPassword(),
                    request.getRol());

            return ResponseEntity.ok(
                    new ApiResponse<>(201, "Usuario registrado exitosamente", guardado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(400, e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        return autenticarUsuario.ejecutar(email, password)
                .map(usuario -> {
                    String token = jwtProvider.generarToken(usuario.getEmail(), usuario.getRol().name());
                    return ResponseEntity.ok(
                            new ApiResponse<>(200, "Login exitoso", Map.of("token", token)));
                })
                .orElseGet(() -> ResponseEntity.status(401).body(
                        new ApiResponse<>(401, "Credenciales inválidas", null)));
    }

}
