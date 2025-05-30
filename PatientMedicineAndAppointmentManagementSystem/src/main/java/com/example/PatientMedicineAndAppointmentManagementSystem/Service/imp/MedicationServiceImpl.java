package com.example.PatientMedicineAndAppointmentManagementSystem.Service.imp;

import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.MedicationDTO;

import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.Medication;
import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.Patient;
import com.example.PatientMedicineAndAppointmentManagementSystem.Repository.MedicationRepository;
import com.example.PatientMedicineAndAppointmentManagementSystem.Repository.PatientRepository;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    public MedicationDTO saveMedication(MedicationDTO medicationDto) {
        Medication medication = modelMapper.map(medicationDto, Medication.class);
        if (medicationDto.getPatientId() != null) {
            medication.setPatient(patientRepository.findById(medicationDto.getPatientId()).orElse(null));
        }
        Medication saved = medicationRepository.save(medication);
        return modelMapper.map(saved, MedicationDTO.class);
    }

    @Override
    public List<MedicationDTO> getMedicationsByPatientId(Long patientId) {
        return medicationRepository.findByPatient_PatientId(patientId).stream()
                .map(m -> modelMapper.map(m, MedicationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicationDTO> getAllMedications() {
        return medicationRepository.findAll().stream()
                .map(m -> modelMapper.map(m, MedicationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMedication(Long id) {
        medicationRepository.deleteById(id);
    }
}
