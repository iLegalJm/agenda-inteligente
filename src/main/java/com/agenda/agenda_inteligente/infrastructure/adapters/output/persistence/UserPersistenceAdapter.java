package com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.agenda.agenda_inteligente.application.ports.output.UserPersistencePort;
import com.agenda.agenda_inteligente.domain.model.User;
import com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence.mapper.UserMapper;
import com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        var entity = userMapper.toEntity(user);
        var savedEntity = userRepository.save(entity);
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDomain);
    }
}
