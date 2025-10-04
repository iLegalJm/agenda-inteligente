package com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {

    @NotBlank(message = "El primer nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El primer nombre debe tener entre 2 y 50 caracteres")
    private String primerNombre;

    @Size(max = 50, message = "El segundo nombre no puede tener más de 50 caracteres")
    private String segundoNombre;

    @NotBlank(message = "El apellido paterno no puede estar vacío")
    @Size(min = 2, max = 50, message = "El apellido paterno debe tener entre 2 y 50 caracteres")
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno no puede estar vacío")
    @Size(min = 2, max = 50, message = "El apellido materno debe tener entre 2 y 50 caracteres")
    private String apellidoMaterno;

    @NotNull(message = "La fecha de nacimiento no puede estar vacía")
    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El DNI no puede estar vacío")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener exactamente 8 dígitos")
    private String dni;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
    private String direccion;
}