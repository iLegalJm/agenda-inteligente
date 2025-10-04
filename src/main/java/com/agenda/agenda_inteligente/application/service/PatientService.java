package com.agenda.agenda_inteligente.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agenda.agenda_inteligente.application.ports.input.PatientServicePort;
import com.agenda.agenda_inteligente.application.ports.output.PatientPersistencePort;
import com.agenda.agenda_inteligente.domain.model.Patient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService implements PatientServicePort {

    private final PatientPersistencePort patientPersistencePort;

    @Override
    public Patient get(Long id) {
        return patientPersistencePort.get(id).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    @Override
    public List<Patient> getAll() {
        return patientPersistencePort.getAll();
    }

    @Override
    public Patient save(Patient patient) {
        return patientPersistencePort.save(patient);
    }

    @Override
    public Patient updated(Long id, Patient patient) {
        Patient savePatient = patientPersistencePort.get(id)
            .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        savePatient.setPrimerNombre(patient.getPrimerNombre());
        savePatient.setSegundoNombre(patient.getSegundoNombre());
        savePatient.setApellidoPaterno(patient.getApellidoPaterno());
        savePatient.setApellidoMaterno(patient.getApellidoMaterno());
        savePatient.setFechaNacimiento(patient.getFechaNacimiento());
        savePatient.setDni(patient.getDni());
        savePatient.setDireccion(patient.getDireccion());
        return patientPersistencePort.save(savePatient);
    }

    @Override
    public void delete(Long id) {
        patientPersistencePort.delete(id);
    }
}
