package com.example.PatientMedicineAndAppointmentManagementSystem.Controller;

import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.MedicationDTO;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/medication")
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping("/create")
    public String createMedication(@ModelAttribute MedicationDTO medicationDto) {
        medicationService.saveMedication(medicationDto);
        return "redirect:/patient/dashboard";
    }

    @PostMapping("/delete/{id}")
    public String deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
        return "redirect:/patient/dashboard";
    }
}
