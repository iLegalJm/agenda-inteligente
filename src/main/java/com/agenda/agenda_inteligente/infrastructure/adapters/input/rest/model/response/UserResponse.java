package com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.response;

import com.agenda.agenda_inteligente.domain.model.Rol;

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
public class UserResponse {
    private Long id;
    private String nombre;
    private String email;
    private Rol rol;
}