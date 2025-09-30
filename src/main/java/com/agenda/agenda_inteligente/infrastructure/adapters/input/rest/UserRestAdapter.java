package com.agenda.agenda_inteligente.infrastructure.adapters.input.rest;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.agenda_inteligente.application.ports.input.AuthServicePort;
import com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.mapper.UserRestMapper;
import com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.ApiResponse;
import com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.request.LoginRequest;
import com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.request.RegisterRequest;
import com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.response.TokenResponse;
import com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.response.UserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserRestAdapter {
        private final AuthServicePort servicePort;
        private final UserRestMapper userRestMapper;

        @PostMapping("/register")
        public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {

                var usuario = userRestMapper.toDomain(request);

                var createdUser = servicePort.register(usuario);

                var response = userRestMapper.toResponse(createdUser);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ApiResponse<>(HttpStatus.CREATED.value(),
                                                "Usuario registrado exitosamente", response));
        }

        @PostMapping("/login")
        public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
                Map<String, String> tokens = servicePort.login(loginRequest.getEmail(),
                                loginRequest.getPassword());

                TokenResponse response = TokenResponse.builder()
                                .accessToken(tokens.get("accessToken"))
                                .refreshToken(tokens.get("refreshToken"))
                                .build();

                return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                                "Login exitoso", response));
        }

        @PostMapping("/refresh")
        public ResponseEntity<ApiResponse<TokenResponse>> refresh(@RequestBody Map<String, String> body) {
                String refreshToken = body.get("refreshToken");

                // Ejecutar caso de uso
                Map<String, String> newAccess = servicePort.refreshToken(refreshToken);

                TokenResponse response = TokenResponse.builder()
                                .accessToken(newAccess.get("accessToken"))
                                .refreshToken(refreshToken) // mismo refresh token
                                .build();

                return ResponseEntity.ok(
                                new ApiResponse<>(HttpStatus.OK.value(), "Token renovado", response));
        }

        @PostMapping("/logout")
        public ResponseEntity<ApiResponse<Void>> logout(@RequestBody Map<String, String> body) {
                String refreshToken = body.get("refreshToken");

                servicePort.logout(refreshToken);

                return ResponseEntity.ok(
                                new ApiResponse<>(HttpStatus.OK.value(), "Logout exitoso", null));
        }
}
