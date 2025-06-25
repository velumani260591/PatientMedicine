package com.example.PatientMedicineAndAppointmentManagementSystem.Controller;

import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.AppointmentDTO;

import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.AppointmentHistoryDTO;
import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.DoctorDto;
import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.PatientDto;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.AppointmentService;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.AppointmentServiceHistory;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.DoctorService;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "patient operation ",
        description = "in this code pattern operation work on it"
)

@Controller
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final AppointmentServiceHistory appointmentServiceHistory;

    @Operation(
            summary = "it open ths dashboard of the patient with of make appointment and history"
    )
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String email = authentication.getName();
        PatientDto patient = patientService.findByEmail(email);
        model.addAttribute("patient", patient);
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByPatientEmail(email);
        model.addAttribute("allappointments", appointments);

        return "patient_dashboard";
    }

    @Operation(
            summary = "get the appoiment daetaisl"
    )

    @GetMapping("/make_appointment_page")
    public String make_appointment_page(Model model,Authentication authentication)
    {
        String email=authentication.getName();
        PatientDto patient=patientService.findByEmail(email);
        model.addAttribute("patient", patient);
        List<DoctorDto> alldoctors=doctorService.getAllDoctors();
        model.addAttribute("alldoctors",alldoctors);
        return "make_Appointment";
    }

    @GetMapping("/make_appointment/{id}")
    public String make_appointment(@PathVariable("id") Long id, Model model)
    {
        DoctorDto doctorDto=doctorService.findById(id);
        model.addAttribute("doctor",doctorDto);
        AppointmentDTO appointmentDTO=new AppointmentDTO();
        appointmentDTO.setDoctorId(doctorDto.getDoctorId());
        model.addAttribute("book_appointment",appointmentDTO);
        return "finalBookingAppointmentPage";
    }

    @Operation(
            summary = "it save the appoint the data"
    )

    @PostMapping("/save_Appointment")
    public String saveAppointment(@ModelAttribute("book_appointment") AppointmentDTO appointmentDTO,
                                  Authentication authentication) {
        String email = authentication.getName();
        PatientDto patientDto = patientService.findByEmail(email);

        // Set patientId (doctorId should already be set from the form)
        appointmentDTO.setPatientId(patientDto.getPatientId());

        appointmentService.saveAppointment(appointmentDTO);
        appointmentServiceHistory.saveAppointment(appointmentDTO);
        return "redirect:/patient/dashboard?success";
    }
    @Operation(
            summary = "delete the appointment after the user deiced to deleted"
    )

    @GetMapping("/deleteAppointement/{id}")
    public String deleteAppointment(@PathVariable("id") Long id,Model model)
    {
        appointmentService.deleteAppointment(id);
        return "redirect:/patient/dashboard?delete";
    }

    @GetMapping("/history")
    public String history(Model model, Authentication authentication) {
        String email = authentication.getName();
        PatientDto patient = patientService.findByEmail(email);
        model.addAttribute("patient", patient);
        List<AppointmentHistoryDTO> appointments = appointmentServiceHistory.getAppointmentsByPatientEmail(email);
        model.addAttribute("allappointments", appointments);
        return "history";
    }


}
