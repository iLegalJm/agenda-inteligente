package com.agenda.agenda_inteligente.application.ports.output;

import java.util.List;
import java.util.Optional;

import com.agenda.agenda_inteligente.domain.model.Patient;

public interface PatientPersistencePort {
    Optional<Patient> get(Long id);

    List<Patient> getAll();

    Patient save(Patient patient);

    void delete(Long id);
}
