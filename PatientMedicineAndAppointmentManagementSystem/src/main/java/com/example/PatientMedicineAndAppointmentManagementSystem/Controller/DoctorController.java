package com.example.PatientMedicineAndAppointmentManagementSystem.Controller;

import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.AppointmentDTO;
import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.AppointmentHistoryDTO;
import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.DoctorDto;
import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.PatientDto;
import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.Patient;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.AppointmentService;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.AppointmentServiceHistory;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.DoctorService;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.PatientService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/doctor")
@AllArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final ModelMapper modelMapper;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        // Get logged-in doctor's email
        String email = authentication.getName();

        // Get doctor details
        DoctorDto doctor = doctorService.findByEmail(email);
        model.addAttribute("doctor", doctor);

        // Get all appointments for this doctor
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorEmail(email);
        model.addAttribute("allappointments", appointments);

        // Gather unique patient IDs from appointments
        Set<Long> patientIds = appointments.stream()
                .map(AppointmentDTO::getPatientId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Convert Set to List for repository method
        List<Long> patientIdList = new ArrayList<>(patientIds);

        // Fetch all patients in one query
        List<PatientDto> patients = patientService.findAllByIds(patientIdList);

        // Create a map for quick lookup in the template
        Map<Long, PatientDto> patientMap = patients.stream()
                .collect(Collectors.toMap(PatientDto::getPatientId, p -> p));
        model.addAttribute("patientMap", patientMap);

        return "doctor_dashboard";
    }

    @GetMapping("/canceltheappoinment/{id}")
    public String canceltheappoinment(@PathVariable("id") Long id)
    {
        appointmentService.deleteAppointment(id);
        return "redirect:/doctor/dashboard";
    }

    @GetMapping("/accpetdPatient/{appointmentId}/{patientId}")
    public String acceptpatienr(@PathVariable("appointmentId") Long appointmentId,
                                @PathVariable("patientId") Long patientId,
                                Model model) {
        PatientDto patientDto = patientService.findById(patientId);
        if (patientDto == null) {
            model.addAttribute("error", "Patient not found");
            return "error_page"; // Use your error page
        }
        patientDto.setAppointmentId(appointmentId); // Set appointmentId in DTO
        model.addAttribute("appointmentpatient", patientDto);
        return "docotr_patient_checkinarea";
    }


    @PostMapping("/savethepatientmedicaldetils")
    public String savethepatientmedicaldetils(@ModelAttribute("appointmentpatient") PatientDto patientDto, Model model)
    {
        patientService.updateolduser(patientDto);
        Patient patient=modelMapper.map(patientDto,Patient.class);
//        AppointmentDTO appointmentDTO=appointmentService.getapptiomentbyId(appointmentId);
//        appointmentServiceHistory.saveAppointment(appointmentDTO);
        appointmentService.deleteAppointment(patientDto.getAppointmentId());
        return "redirect:/doctor/dashboard";
    }
}
