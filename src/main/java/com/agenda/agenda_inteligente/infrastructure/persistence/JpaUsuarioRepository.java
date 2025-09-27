package com.agenda.agenda_inteligente.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUsuarioRepository extends JpaRepository<JpaUsuarioEntity, Long> {
    Optional<JpaUsuarioEntity> findByEmail(String email);
}
