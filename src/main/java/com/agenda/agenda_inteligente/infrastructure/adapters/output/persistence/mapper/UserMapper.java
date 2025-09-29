package com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.agenda.agenda_inteligente.domain.model.User;
import com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(User user);

    User toDomain(UserEntity entity);

    List<User> toDomainList(List<UserEntity> entities);
}
