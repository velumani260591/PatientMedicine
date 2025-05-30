package com.example.PatientMedicineAndAppointmentManagementSystem.Controller;


import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.AppointmentDTO;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController
{
    private final AppointmentService appointmentService;

    @PostMapping("/create")
    public String createAppointment(@ModelAttribute AppointmentDTO appointmentDto) {
        appointmentService.saveAppointment(appointmentDto);
        return "redirect:/patient/dashboard";
    }

    @PostMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/patient/dashboard";
    }
}
