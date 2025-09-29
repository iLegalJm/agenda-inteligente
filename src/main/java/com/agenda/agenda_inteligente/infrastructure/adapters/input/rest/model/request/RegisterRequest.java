package com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.request;

import com.agenda.agenda_inteligente.domain.model.Rol;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotEmpty(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "Debe ser un correo válido")
    @NotEmpty(message = "El email no puede estar vacío")
    private String email;

    @NotEmpty(message = "La contraseña no puede estar vacía")
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private Rol rol; // ADMIN, MEDICO, PACIENTE
}