    package com.example.PatientMedicineAndAppointmentManagementSystem.Service.imp;

    import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.DoctorDto;
    import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.Doctor;
    import com.example.PatientMedicineAndAppointmentManagementSystem.Repository.DoctorRepository;
    import com.example.PatientMedicineAndAppointmentManagementSystem.Service.DoctorService;
    import lombok.RequiredArgsConstructor;
    import org.modelmapper.ModelMapper;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    public class DoctorServiceImpl implements DoctorService {

        private final DoctorRepository doctorRepository;
        private final ModelMapper modelMapper;

        @Override
        public DoctorDto saveDoctor(DoctorDto doctorDto) {
            Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
            Doctor saved = doctorRepository.save(doctor);
            return modelMapper.map(saved, DoctorDto.class);
        }

        @Override
        public DoctorDto findByEmail(String email) {
            Doctor doctor = doctorRepository.findByDoctorEmail(email);
            return (doctor != null) ? modelMapper.map(doctor, DoctorDto.class) : null;
        }

        @Override
        public DoctorDto findById(Long id) {
            return doctorRepository.findById(id)
                    .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                    .orElse(null);
        }

        @Override
        public List<DoctorDto> getAllDoctors() {
            return doctorRepository.findAll().stream()
                    .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                    .collect(Collectors.toList());
        }


    }
