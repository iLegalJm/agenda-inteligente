package com.agenda.agenda_inteligente.domain.model;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshToken {
    private Long id;
    private String token;
    private Long userId;
    private Instant expiryDate;
    private boolean revoked;
}
