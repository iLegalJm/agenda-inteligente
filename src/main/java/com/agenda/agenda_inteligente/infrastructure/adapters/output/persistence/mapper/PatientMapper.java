package com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.agenda.agenda_inteligente.domain.model.Patient;
import com.agenda.agenda_inteligente.infrastructure.adapters.output.persistence.entity.PatientEntity;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientEntity toEntity(Patient patient);

    Patient toDomain(PatientEntity entity);

    List<Patient> toDomainList(List<PatientEntity> entities);
}
