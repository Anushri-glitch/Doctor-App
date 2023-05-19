package com.example.Doctor.Controller;

import com.example.Doctor.Model.Doctor;
import com.example.Doctor.Service.DoctorService;
import jakarta.annotation.Nullable;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/Doctor-app")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    //URL -  http://localhost:8080/Doctor-app/Doctor
    @PostMapping("/Doctor")
    public ResponseEntity<String> saveDoctor(@RequestBody String requestDoctor){
        JSONObject json = new JSONObject(requestDoctor);
        List<String> validationList = validateDoctor(json);

        if(validationList.isEmpty()){
            Doctor doctor = setDoctor(json);
            doctorService.saveDoctor(doctor);
            return new ResponseEntity<>("Doctor Saved", HttpStatus.CREATED);
        }
        else{
            String[] answer = Arrays.copyOf(validationList.toArray(), validationList.size(), String[].class);
            return new ResponseEntity<>("please pass mandatory parameters" + Arrays.toString(answer),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getDoctors")
    public List<Doctor> getDoctor(@Nullable @RequestParam String doctorId){
        return doctorService.getDoctor(doctorId);
    }

    @GetMapping("/getDoctorById")
    public Doctor getDoctorById(@RequestParam String doctorId){
        return doctorService.getDoctorById(doctorId);
    }

    private List<String> validateDoctor(JSONObject json){
        List<String> errorList = new ArrayList<>();

        if(!json.has("doctorId")){
            errorList.add("doctorId");
        }

        if(!json.has("doctorName")) {
            errorList.add("doctorName");
        }

        if(!json.has("specialisation")){
            errorList.add("specialisation");
        }

        return errorList;
    }

    public Doctor setDoctor (JSONObject json){

        Doctor doctor = new Doctor();

        String doctorId = json.getString("doctorId");
        doctor.setDoctorId(Integer.valueOf(doctorId));

        String doctorName = json.getString("doctorName");
        doctor.setDoctorName(doctorName);

        String specs = json.getString("specialisation");
        doctor.setSpecialisation(specs);

        if(json.has("experience")){
            String exp = json.getString("experience");
            doctor.setExperience(exp);
        }
        return doctor;
    }
}
