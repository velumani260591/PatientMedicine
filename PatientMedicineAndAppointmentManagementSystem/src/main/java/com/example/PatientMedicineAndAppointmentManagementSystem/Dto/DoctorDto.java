package com.example.PatientMedicineAndAppointmentManagementSystem.Dto;

import lombok.Data;

@Data
public class DoctorDto {
    private Long doctorId;
    private String doctorName;
    private String specialization;
    private String doctorEmail;
    private String doctorPassword;
    private String doctorContact;
}
