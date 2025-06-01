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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
@Tag(
        name = "Make all doctor opertions or controller in it"
)

@Controller
@RequestMapping("/doctor")
@AllArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final ModelMapper modelMapper;

    @Operation(
            summary = "it open the dashboard of doctor with appointment "
    )
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {

        String email = authentication.getName();


        DoctorDto doctor = doctorService.findByEmail(email);
        model.addAttribute("doctor", doctor);


        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorEmail(email);
        model.addAttribute("allappointments", appointments);


        Set<Long> patientIds = appointments.stream()
                .map(AppointmentDTO::getPatientId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());


        List<Long> patientIdList = new ArrayList<>(patientIds);


        List<PatientDto> patients = patientService.findAllByIds(patientIdList);


        Map<Long, PatientDto> patientMap = patients.stream()
                .collect(Collectors.toMap(PatientDto::getPatientId, p -> p));
        model.addAttribute("patientMap", patientMap);

        return "doctor_dashboard";
    }


    @Operation(
            summary = "it delete the appointment if docotrs select cancel"
    )

    @GetMapping("/canceltheappoinment/{id}")
    public String canceltheappoinment(@PathVariable("id") Long id)
    {
        appointmentService.deleteAppointment(id);
        return "redirect:/doctor/dashboard";
    }


    @Operation(
            summary = "it accept the appointment for doctor so he can get treatment for doctor"
    )

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


    @Operation(
            summary = "after the treatment the doctor give some medical and after save it store the new medical records data in patient database"
    )
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
