package com.example.Doctor.Service;

import com.example.Doctor.Dao.IDoctorRepository;
import com.example.Doctor.Model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    IDoctorRepository repository;

    public Doctor saveDoctor(Doctor doctor){
        String doctorName = doctor.getDoctorName();
        doctorName = "Dr. " + doctorName;
        doctor.setDoctorName(doctorName);
        return repository.save(doctor);
    }

    public List<Doctor> getDoctor(String doctorId) {
       List<Doctor> doctorList;
       if(null != doctorId){
           doctorList = new ArrayList<>();
           doctorList.add(repository.findById(Integer.valueOf(doctorId)).get());
       }
       else{
           doctorList = repository.findAll();
       }
       return doctorList;
    }

    public Doctor getDoctorById(String doctorId){
        return repository.findById(Integer.valueOf(doctorId)).get();
    }
}
