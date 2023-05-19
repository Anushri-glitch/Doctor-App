package com.example.Doctor.Service;

import com.example.Doctor.Dao.IPatientRepository;
import com.example.Doctor.Model.AppointmentKey;
import com.example.Doctor.Model.AuthenticationToken;
import com.example.Doctor.Model.Doctor;
import com.example.Doctor.Model.Patient;
import com.example.Doctor.dto.SignInInput;
import com.example.Doctor.dto.SignInOutput;
import com.example.Doctor.dto.SignUpInput;
import com.example.Doctor.dto.SignUpOutput;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    IPatientRepository patientRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    DoctorService doctorService;

    /* public void savePatient(Patient patient){
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
    }*/

    public SignUpOutput signUp(SignUpInput signUpDto) { // get the user details for sign up : signUpDto

        //check if user exists or not based on email
        Patient patient = patientRepo.findFirstByPatientEmail(signUpDto.getUserEmail());//alternative : exist by true/false

        if(patient != null)
        {
            throw new IllegalStateException("Patient already exists!!!!...sign in instead");
        }


        //encryption
        String encryptedPassword = null;
        try {
            encryptedPassword = encryptPassword(signUpDto.getUserPassword());//takes  a string and encrypts it...
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }

        //save the user

        patient = new Patient(signUpDto.getUserFirstName(),
                signUpDto.getUserLastName(),signUpDto.getUserEmail(),
                encryptedPassword, signUpDto.getUserContact());
        patientRepo.save(patient);

        //token creation and saving

        AuthenticationToken token = new AuthenticationToken(patient);

        authenticationService.saveToken(token);

        return new SignUpOutput("Patient registered","Patient created successfully");


    }

    private String encryptPassword(String userPassword) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(userPassword.getBytes());
        byte[] digested =  md5.digest();

        String hash = DatatypeConverter.printHexBinary(digested);
        return hash;
    }


    public SignInOutput signIn(SignInInput signInDto) {

        //get email

        Patient patient = patientRepo.findFirstByPatientEmail(signInDto.getPatientEmail());

        if(patient == null)
        {
            throw new IllegalStateException("User invalid!!!!...sign up instead");
        }

        //encrypt the password

        String encryptedPassword = null;

        try {
            encryptedPassword = encryptPassword(signInDto.getPatientPassword());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }



        //match it with database encrypted password

        boolean isPasswordValid = encryptedPassword.equals(patient.getPatientPassword());

        if(!isPasswordValid)
        {
            throw new IllegalStateException("User invalid!!!!...sign up instead");
        }

        //figure out the token

        AuthenticationToken authToken = authenticationService.getToken(patient);

        //set up output response

        return new SignInOutput("Authentication Successfull !!!",authToken.getToken());


    }

    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    public void cancelAppointment(AppointmentKey key) {

        appointmentService.cancelAppointment(key);

    }
}
