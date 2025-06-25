package com.example.PatientMedicineAndAppointmentManagementSystem.Service;

import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.AppointmentDTO;
import java.util.List;

public interface AppointmentService {
    AppointmentDTO saveAppointment(AppointmentDTO appointmentDto);
    List<AppointmentDTO> getAppointmentsByPatientEmail(String email);
    List<AppointmentDTO> getAppointmentsByDoctorEmail(String email);
    List<AppointmentDTO> getAllAppointments();
    void deleteAppointment(Long id);
//    List<AppointmentDTO> getAppointmentsByDoctorId(Long id);
List<AppointmentDTO> getAppointmentsByDoctorEmails(String email);
    AppointmentDTO getapptiomentbyId(Long id);
}
