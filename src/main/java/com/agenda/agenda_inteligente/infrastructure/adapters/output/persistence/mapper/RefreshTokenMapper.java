package com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.agenda.agenda_inteligente.domain.model.RefreshToken;
import com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence.entity.RefreshTokenEntity;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {
    RefreshTokenEntity toEntity(RefreshToken refreshToken);

    RefreshToken toDomain(RefreshTokenEntity refreshTokenEntity);

    List<RefreshToken> toDomainList(List<RefreshTokenEntity> entities);
}
