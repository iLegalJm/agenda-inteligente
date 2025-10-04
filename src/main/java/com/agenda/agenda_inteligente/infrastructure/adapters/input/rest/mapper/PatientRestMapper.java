package com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.agenda.agenda_inteligente.domain.model.Patient;
import com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.request.PatientRequest;
import com.agenda.agenda_inteligente.infrastructure.adapters.input.rest.model.response.PatientResponse;

@Mapper(componentModel = "spring")
public interface PatientRestMapper {

    @Mapping(target = "id", ignore = true)
    Patient toDomain(PatientRequest request);

    PatientResponse toResponse(Patient patient);

    List<PatientResponse> toResponseList(List<Patient> patientsList);
}
