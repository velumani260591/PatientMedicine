package com.example.PatientMedicineAndAppointmentManagementSystem.Controller;

import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.DoctorDto;
import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.Doctor;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "add the doctors",
        description = "adding the doctor details hospital database and creating a account to doctor  "
)
@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private PasswordEncoder passwordEncoder;
    private DoctorService doctorService;

    @Operation(
            summary = "open the main dashboard of admin page it contains add doctors option"
    )
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        List<DoctorDto> doctors=doctorService.getAllDoctors();
        model.addAttribute("totaldoctors",doctors);
        return "admin-dashboard"; // Make sure you have admin-dashboard.html in templates
    }

    @Operation(
            summary = "in this areas it take details of the new doctor for admin"
    )

    @GetMapping("/adddoctors")
    public String adddocotor(Model model)
    {
        DoctorDto doctorDto=new DoctorDto();
        model.addAttribute("adddocotr",doctorDto);
        return "adddoctor";
    }

    @Operation(
            summary = "it save the new details for the doctor in doctor databse"
    )
    @PostMapping("/savethedoctordetasils")
    public String savethedoctordetasils(@ModelAttribute("adddocotr") DoctorDto doctorDto, BindingResult bindingResult, Model model)
    {
        if(doctorService.findByEmail(doctorDto.getDoctorEmail())!=null)
        {
            bindingResult.rejectValue("doctorEmail","email.exists","This email is already used. Please use another email.");
        }
        if(bindingResult.hasErrors())
        {
            return "adddoctor";
        }
        doctorDto.setDoctorPassword(passwordEncoder.encode(doctorDto.getDoctorPassword()));
        doctorService.saveDoctor(doctorDto);
        return "redirect:/admin/dashboard";
    }


}
