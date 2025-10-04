package com.agenda.agenda_inteligente.application.ports.input;

import java.util.List;

import com.agenda.agenda_inteligente.domain.model.Patient;

public interface PatientServicePort {
    Patient get(Long id);

    List<Patient> getAll();

    Patient save(Patient patient);

    Patient updated(Long id, Patient patient);

    void delete(Long id);
}
