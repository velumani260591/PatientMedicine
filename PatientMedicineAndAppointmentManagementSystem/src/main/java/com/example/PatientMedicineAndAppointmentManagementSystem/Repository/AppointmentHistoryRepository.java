package com.example.PatientMedicineAndAppointmentManagementSystem.Repository;

import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.Appointment;
import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.AppointmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentHistoryRepository extends JpaRepository<AppointmentHistory,Long> {
    List<Appointment> findByPatient_PatientId(Long patientId);
}
