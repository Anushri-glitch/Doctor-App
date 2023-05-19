package com.example.Doctor.Controller;

import com.example.Doctor.Dao.IDoctorRepository;
import com.example.Doctor.Model.Doctor;
import com.example.Doctor.Model.Patient;
import com.example.Doctor.Service.AppointmentService;
import com.example.Doctor.Service.IAuthService;
import com.example.Doctor.Service.PatientService;
import com.example.Doctor.dto.SignInInput;
import com.example.Doctor.dto.SignInOutput;
import com.example.Doctor.dto.SignUpInput;
import com.example.Doctor.dto.SignUpOutput;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class PatientController {

    @Autowired
    IDoctorRepository doctorRepository;

    @Autowired
    PatientService patientService;

    @Autowired
    IAuthService authService;

    @Autowired
    AppointmentService appointmentService;

    //URL - http://localhost:8080/Patient
    /* @PostMapping("/Patient")
    public String savePatient(@RequestBody String patientRequest){
        JSONObject json = new JSONObject(patientRequest);
        Patient patient = setPatient(json);
        patientService.savePatient(patient);

        return "saved Patient";
    }

    private Patient setPatient(JSONObject json){
        Patient patient = new Patient();
        patient.setPatientId(json.getInt("patientId"));
        patient.setPatientName(json.getString("patientName"));
        patient.setAge(json.getInt("age"));
        patient.setPhoneNumber(json.getString("phoneNumber"));
        patient.setDiseaseType(json.getString("diseaseType"));
        patient.setGender(json.getString("gender"));

        //we convert MilliSecond into Timestamp
        Timestamp currTime = new Timestamp(System.currentTimeMillis());
        patient.setAdmitDate(currTime);

        String doctorId = json.getString("doctorId");
        Doctor doctor = doctorRepository.findById(Integer.valueOf(doctorId)).get();
        patient.setDoctor(doctor);
        return patient;
    } */

    /* When we have to pass PatientId : then the response is only total detail of that patient
       When we have to pass DoctorId : then the response is all the patients of that doctor
       When the admin login that : then it passes nothing and he received the List Of Patients
     */
    @GetMapping(value= "/patient")
    public ResponseEntity<String> getPatient(@Nullable @RequestParam String doctorId, @Nullable @RequestParam String patientId){
        JSONArray patientDetails = patientService.getPatient();
        return new ResponseEntity<>(patientDetails.toString(),HttpStatus.OK);
    }

    //sign up

    // sign up input
    //sign up output
    @PostMapping("/signup")
    public SignUpOutput signup(@RequestBody SignUpInput signUpDto)
    {
        return patientService.signUp(signUpDto);
    }

    //sign in

    @PostMapping("/signin")
    public SignInOutput signIn(@RequestBody SignInInput signInDto)
    {
        return patientService.signIn(signInDto);
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors(@RequestParam String userEmail, @RequestParam String token)
    {
        HttpStatus status;
        List<Doctor> allDoctors = null;
        //authenticate

        //token : calculate token -> find email in Db corr to this token-> match the emails
        if(authService.authenticate(userEmail,token))
        {

            allDoctors =  patientService.getAllDoctors();
            status = HttpStatus.OK;
        }
        else
        {
            status = HttpStatus.FORBIDDEN;
        }



        return new ResponseEntity<List<Doctor>>(allDoctors, status);
    }


    //todo : move the create appointment in Appointment controller in here along with authentication...!

    //delete my appointment :

    @DeleteMapping("appointment")
    ResponseEntity<Void> cancelAppointment(@RequestParam String userEmail, @RequestParam String token, @RequestBody AppointmentKey key)
    {

        HttpStatus status;
        if(authService.authenticate(userEmail,token))
        {
            patientService.cancelAppointment(key);
            status = HttpStatus.OK;
        }
        else
        {
            status = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<Void>(status);

    }


}
