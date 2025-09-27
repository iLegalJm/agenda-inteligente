package com.agenda.agenda_inteligente.interfaces.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "Debe ser un correo válido")
    private String email;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    private String rol; // ADMIN, MEDICO, PACIENTE
}
