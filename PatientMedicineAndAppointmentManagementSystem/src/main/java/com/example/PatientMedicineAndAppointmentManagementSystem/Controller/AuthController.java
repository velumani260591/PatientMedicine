package com.example.PatientMedicineAndAppointmentManagementSystem.Controller;

import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.PatientDto;
import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.Patient;
import com.example.PatientMedicineAndAppointmentManagementSystem.Repository.PatientRepository;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.PatientService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.Authenticator;

@Controller
@AllArgsConstructor
public class AuthController {

    private PatientService patientService;

    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Invalid email or password.");
        }
        return "loginpages";
    }




    @GetMapping("/registration")
    public String Registration(Model model)
    {
        PatientDto patient=new PatientDto();
        model.addAttribute("patient",patient);
        return "registration";
    }
    @PostMapping("/saveRegistration")
    public String saveRegistration(@ModelAttribute("patient") PatientDto patientDto,Model model)
    {
        patientDto.setPatientPassword(passwordEncoder.encode(patientDto.getPatientPassword()));
        patientService.savePatient(patientDto);
        return "redirect:/login";
    }



}
