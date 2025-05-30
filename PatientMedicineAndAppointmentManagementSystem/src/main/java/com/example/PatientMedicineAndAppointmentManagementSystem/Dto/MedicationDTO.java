package com.example.PatientMedicineAndAppointmentManagementSystem.Dto;

import lombok.Data;

@Data
public class MedicationDTO {
    private Long id;
    private String name;
    private String dosage;
    private Long patientId;
    private String patientName;
}
