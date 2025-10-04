package com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence.entity.PatientEntity;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

}
