package com.agenda.agenda_inteligente.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Rol {
    ADMIN,
    MEDICO,
    PACIENTE;

    @JsonCreator
    public static Rol fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo");
        }
        try {
            return Rol.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Rol inválido: " + value);
        }
    }
}
