package com.agenda.agenda_inteligente.application.ports.output;

import java.util.Optional;

import com.agenda.agenda_inteligente.domain.model.User;

public interface UserPersistencePort {
    Optional<User> findByEmail(String email);

    User save(User usuario);
}
