package com.agenda.agenda_inteligente.infrastructure.adapters.input.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenda.agenda_inteligente.application.ports.input.PatientServicePort;
import com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.mapper.PatientRestMapper;
import com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.ApiResponse;
import com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.request.PatientRequest;
import com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.response.PatientResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
@CrossOrigin(origins = "*")
public class PatientRestAdapter {

    private final PatientServicePort servicePort;
    private final PatientRestMapper restMapper;

    @GetMapping("/v1/api")
    public ResponseEntity<ApiResponse<List<PatientResponse>>> getAllPatients() {

        var patientsList = servicePort.getAll();

        var response = restMapper.toResponseList(patientsList);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                "Lista de pacientes obtenida exitosamente", response));
    }

    @GetMapping("/v1/api/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> getPatientById(@PathVariable Long id) {

        var patient = servicePort.get(id);

        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(),
                            "Paciente no encontrado", null));
        }

        var response = restMapper.toResponse(patient);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                "Paciente encontrado exitosamente", response));
    }

    @PostMapping("/v1/api")
    public ResponseEntity<ApiResponse<PatientResponse>> createPatient(@Valid @RequestBody PatientRequest request) {
        System.out.println("Peticion recibida: " + request);

        var patient = restMapper.toDomain(request);
        System.out.println("Paciente mapeado: " + patient);

        var createdPatient = servicePort.save(patient);
        System.out.println("Paciente creado: " + createdPatient);

        var response = restMapper.toResponse(createdPatient);
        System.out.println("Respuesta mapeada: " + response);

        var result = ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Usuario registrado exitosamente", response));
        System.out.println("Respuesta final: " + result);

        return result;
    }

    @PutMapping("/v1/api/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> updatePatient(@PathVariable Long id,
            @Valid @RequestBody PatientRequest request) {
        var patient = restMapper.toDomain(request);
        var updatedPatient = servicePort.updated(id, patient);
        var response = restMapper.toResponse(updatedPatient);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                "Paciente actualizado exitosamente", response));
    }

    @DeleteMapping("/v1/api/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePatient(@PathVariable Long id) {
        servicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}
