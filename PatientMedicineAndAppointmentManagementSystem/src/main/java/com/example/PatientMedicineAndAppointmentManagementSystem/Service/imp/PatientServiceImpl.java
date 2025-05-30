package com.example.PatientMedicineAndAppointmentManagementSystem.Service.imp;

import com.example.PatientMedicineAndAppointmentManagementSystem.Dto.PatientDto;
import com.example.PatientMedicineAndAppointmentManagementSystem.Entity.Patient;
import com.example.PatientMedicineAndAppointmentManagementSystem.Repository.PatientRepository;
import com.example.PatientMedicineAndAppointmentManagementSystem.Service.PatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    public PatientDto savePatient(PatientDto patientDto) {
        Patient patient = modelMapper.map(patientDto, Patient.class);
        Patient saved = patientRepository.save(patient);
        return modelMapper.map(saved, PatientDto.class);
    }
    @Override
    public PatientDto updateolduser(PatientDto patientDto)
    {
        Patient existingpatient=patientRepository.findById(patientDto.getPatientId()).orElseThrow(()-> new RuntimeException("user Not found"));;
        existingpatient.setPatientFirstName(patientDto.getPatientFirstName());
        existingpatient.setPatientLastName(patientDto.getPatientLastName());
        existingpatient.setPatientEmail(patientDto.getPatientEmail());
//        existingpatient.setPatientPassword(patientDto.getPatientPassword());
        existingpatient.setPatientContact(patientDto.getPatientContact());
        existingpatient.setMedicalHistory(patientDto.getMedicalHistory());

       Patient saveddata= patientRepository.save(existingpatient);
       return modelMapper.map(saveddata,PatientDto.class);
    }

    @Override
    public PatientDto findByEmail(String email) {
        Patient patient = patientRepository.findByPatientEmail(email);
        return (patient != null) ? modelMapper.map(patient, PatientDto.class) : null;
    }

    @Override
    public PatientDto findById(Long id) {
        Optional<Patient> patient=patientRepository.findById(id);
        return modelMapper.map(patient,PatientDto.class);
//        return patientRepository.findById(id)
//                .map(patient -> modelMapper.map(patient, PatientDto.class))
//                .orElse(null);
    }

    @Override
    public List<PatientDto> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patient -> modelMapper.map(patient, PatientDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<PatientDto> findAllByIds(Iterable<Long> ids) {
        // Convert Iterable to List
        List<Long> idList = new ArrayList<>();
        ids.forEach(idList::add);

        return patientRepository.findAllByPatientIdIn(idList)
                .stream()
                .map(patient -> modelMapper.map(patient, PatientDto.class))
                .toList();
    }




}
