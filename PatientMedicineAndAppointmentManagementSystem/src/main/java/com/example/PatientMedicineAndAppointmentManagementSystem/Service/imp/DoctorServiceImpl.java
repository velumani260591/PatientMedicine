    package com.example.PatientMedicineAndAppointmentManagementSystem.Service.imp;

    import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.DoctorDto;
    import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.Doctor;
    import com.example.PatientMedicineAndAppointmentManagementSystem.Repository.DoctorRepository;
    import com.example.PatientMedicineAndAppointmentManagementSystem.Service.DoctorService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import lombok.RequiredArgsConstructor;
    import org.modelmapper.ModelMapper;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;

    @Tag(
            name = "This file performs save, delete, and get operations."
    )
    @Service
    @RequiredArgsConstructor
    public class DoctorServiceImpl implements DoctorService {

        private final DoctorRepository doctorRepository;
        private final ModelMapper modelMapper;


        @Operation(
                summary = "change the doctorDto to docto entity and save data"
        )
        @Override
        public DoctorDto saveDoctor(DoctorDto doctorDto) {
            Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
            Doctor saved = doctorRepository.save(doctor);
            return modelMapper.map(saved, DoctorDto.class);
        }

        @Operation(
                summary = "find doctor by using email "
        )
        @Override
        public DoctorDto findByEmail(String email) {
            Doctor doctor = doctorRepository.findByDoctorEmail(email);
            return (doctor != null) ? modelMapper.map(doctor, DoctorDto.class) : null;


        }
        @Operation(
                summary = "find doctor by using id "
        )
        @Override
        public DoctorDto findById(Long id) {
            return doctorRepository.findById(id)
                    .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                    .orElse(null);
        }

        @Operation(
                summary = "get all doctor form doctor database "
        )
        @Override
        public List<DoctorDto> getAllDoctors() {
            return doctorRepository.findAll().stream()
                    .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                    .collect(Collectors.toList());



        }


    }
