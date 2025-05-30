package com.example.PatientMedicineAndAppointmentManagementSystem.Controller;

import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.DoctorDto;
import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.Doctor;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.DoctorService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private PasswordEncoder passwordEncoder;
    private DoctorService doctorService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        List<DoctorDto> doctors=doctorService.getAllDoctors();
        model.addAttribute("totaldoctors",doctors);
        return "admin-dashboard"; // Make sure you have admin-dashboard.html in templates
    }

    @GetMapping("/adddoctors")
    public String adddocotor(Model model)
    {
        DoctorDto doctorDto=new DoctorDto();
        model.addAttribute("adddocotr",doctorDto);
        return "adddoctor";
    }

    @PostMapping("/savethedoctordetasils")
    public String savethedoctordetasils(@ModelAttribute("adddocotr") DoctorDto doctorDto ,Model model)
    {
        doctorDto.setDoctorPassword(passwordEncoder.encode(doctorDto.getDoctorPassword()));
        doctorService.saveDoctor(doctorDto);
        return "redirect:/admin/dashboard";
    }


}
