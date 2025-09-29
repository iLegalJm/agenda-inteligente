package com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.agenda.agenda_inteligente.domain.model.User;
import com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.request.RegisterRequest;
import com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserRestMapper {

    @Mapping(target = "id", ignore = true)
    User toDomain(RegisterRequest request);

    UserResponse toResponse(User user);
}
