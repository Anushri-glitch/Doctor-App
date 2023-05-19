package com.example.Doctor.Service;

import com.example.Doctor.Dao.IPatientRepository;
import com.example.Doctor.Model.Patient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    IPatientRepository patientRepository;

    public void savePatient(Patient patient){
        patientRepository.save(patient);
    }

    public JSONArray getPatient(){
        List<Patient> patientList = patientRepository.findAll();
         JSONArray patientArr = new JSONArray();

         for(Patient patient : patientList){
             JSONObject jsonObject = new JSONObject();

             jsonObject.put("patientId",patient.getPatientId());
             jsonObject.put("patientName",patient.getPatientName());
             jsonObject.put("age",patient.getAge());
             jsonObject.put("phoneNumber",patient.getPhoneNumber());
             jsonObject.put("DiseaseType",patient.getDiseaseType());
             jsonObject.put("Gender",patient.getGender());
             jsonObject.put("AdmitDate",patient.getAdmitDate());
             jsonObject.put("doctorId",patient.getDoctor());
             patientArr.put(jsonObject);
         }
         return patientArr;
    }
}
