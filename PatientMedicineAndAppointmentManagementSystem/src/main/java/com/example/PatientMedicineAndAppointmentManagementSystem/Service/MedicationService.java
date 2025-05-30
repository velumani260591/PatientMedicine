package com.example.PatientMedicineAndAppointmentManagementSystem.Service;

import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.MedicationDTO;

import java.util.List;

public interface MedicationService {
    MedicationDTO saveMedication(MedicationDTO medicationDto);
    List<MedicationDTO> getMedicationsByPatientId(Long patientId);
    List<MedicationDTO> getAllMedications();
    void deleteMedication(Long id);
}
