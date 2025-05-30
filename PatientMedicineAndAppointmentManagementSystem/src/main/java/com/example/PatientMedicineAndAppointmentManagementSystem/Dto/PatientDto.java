package com.example.PatientMedicineAndAppointmentManagementSystem.Dto;

import lombok.Data;

@Data
public class PatientDto {
    private Long patientId;
    private String patientFirstName;
    private String patientLastName;
    private String patientEmail;
    private String patientPassword;
    private String patientContact;
    private String medicalHistory;
    private Long appointmentId;

}
