package com.agenda.agenda_inteligente.application.ports.output;

import java.util.Optional;

import com.agenda.agenda_inteligente.domain.model.RefreshToken;

public interface RefreshTokenPersistencePort {
    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByToken(String token);

    void revoke(String token);
}
