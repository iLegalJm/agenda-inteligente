package com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.agenda.agenda_inteligente.application.ports.output.PatientPersistencePort;
import com.agenda.agenda_inteligente.domain.model.Patient;
import com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence.mapper.PatientMapper;
import com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PatientPersistenceAdapter implements PatientPersistencePort {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public Optional<Patient> get(Long id) {
        return patientRepository.findById(id).map(patientMapper::toDomain);
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll().stream().map(patientMapper::toDomain).toList();
    }

    @Override
    public Patient save(Patient patient) {
        var entity = patientMapper.toEntity(patient);
        var savedEntity = patientRepository.save(entity);

        return patientMapper.toDomain(savedEntity);
    }

    @Override
    public void delete(Long id) {
        patientRepository.deleteById(id);
    }
}
