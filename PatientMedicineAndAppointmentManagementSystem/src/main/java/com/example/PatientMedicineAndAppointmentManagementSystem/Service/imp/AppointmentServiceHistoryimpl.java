package com.example.PatientMedicineAndAppointmentManagementSystem.Service.imp;

import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.AppointmentDTO;
import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.AppointmentHistoryDTO;
import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.Appointment;
import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.AppointmentHistory;
import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.Doctor;
import com.example.PatientMedicineAndAppointmentManagementSystem.Repository.AppointmentHistoryRepository;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.AppointmentServiceHistory;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppointmentServiceHistoryimpl implements AppointmentServiceHistory
{
    private final AppointmentHistoryRepository appointmentHistoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public AppointmentHistoryDTO saveAppointment(AppointmentDTO appointmentDTO) {
        AppointmentHistory appointmentHistory=modelMapper.map(appointmentDTO,AppointmentHistory.class);
        appointmentHistoryRepository.save(appointmentHistory);
        return modelMapper.map(appointmentHistory,AppointmentHistoryDTO.class);
    }


}
