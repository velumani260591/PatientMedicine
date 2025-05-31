package com.example.PatientMedicineAndAppointmentManagementSystem.Repository;

import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByPatientEmail(String findByPatientEmail);
    List<Patient> findAllByPatientIdIn(List<Long> patientIds);




}
