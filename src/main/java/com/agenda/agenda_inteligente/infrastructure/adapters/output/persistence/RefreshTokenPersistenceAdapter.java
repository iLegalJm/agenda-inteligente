package com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.agenda.agenda_inteligente.application.ports.output.RefreshTokenPersistencePort;
import com.agenda.agenda_inteligente.domain.model.RefreshToken;
import com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence.entity.RefreshTokenEntity;
import com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence.mapper.RefreshTokenMapper;
import com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RefreshTokenPersistenceAdapter implements RefreshTokenPersistencePort {
    private final RefreshTokenRepository repository;
    private final RefreshTokenMapper mapper;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        RefreshTokenEntity entity = mapper.toEntity(refreshToken);
        RefreshTokenEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return repository.findByToken(token).map(mapper::toDomain);
    }

    @Override
    public void revoke(String token) {
        repository.findByToken(token).ifPresent(entity -> {
            entity.setRevoked(true);
            repository.save(entity);
        });
    }
}
