package com.example.PatientMedicineAndAppointmentManagementSystem.Dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentHistoryDTO {
    private Long appointmentId;
    private LocalDateTime appointmentDateTime;
    private Long patientId;
    private String patientFirstName;
    private Long doctorId;
    private String doctorName;
}

