package com.example.PatientMedicineAndAppointmentManagementSystem.Controller;

import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.PatientDto;
import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.Patient;
import com.example.PatientMedicineAndAppointmentManagementSystem.Repository.PatientRepository;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.Authenticator;

@Tag(
        name = "permission"

)
@Controller
@AllArgsConstructor
public class AuthController {

    private PatientService patientService;

    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;


    @Operation(
            summary = "it open the login page"
    )
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Invalid email or password.");
        }
        return "loginpages";
    }




    @Operation(
            summary = "in this it take the registration page full details"
    )
    @GetMapping("/registration")
    public String Registration(Model model)
    {
        PatientDto patient=new PatientDto();
        model.addAttribute("patient",patient);
        return "registration";
    }


    @Operation(
            summary = "it saved the registration detail in to patient database"
    )
    @PostMapping("/saveRegistration")
    public String saveRegistration(@ModelAttribute("patient") PatientDto patientDto,BindingResult bindingResult,Model model)
    {
        if(patientService.findByEmail(patientDto.getPatientEmail())!=null)
        {
            bindingResult.rejectValue("patientEmail","Email.exists","This email is already registered. Please use another email.");
        }
        if(bindingResult.hasErrors())
        {
            return "registration";
        }

        patientDto.setPatientPassword(passwordEncoder.encode(patientDto.getPatientPassword()));
        patientService.savePatient(patientDto);
        return "redirect:/login";
    }



}
