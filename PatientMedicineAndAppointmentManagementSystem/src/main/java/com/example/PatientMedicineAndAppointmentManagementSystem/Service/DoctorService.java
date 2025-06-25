package com.example.PatientMedicineAndAppointmentManagementSystem.Service;

import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.DoctorDto;
import java.util.List;

public interface DoctorService {
    DoctorDto saveDoctor(DoctorDto doctorDto);
    DoctorDto findByEmail(String email);
    DoctorDto findById(Long id);
    List<DoctorDto> getAllDoctors();
}
