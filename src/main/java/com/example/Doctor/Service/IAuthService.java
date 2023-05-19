package com.example.Doctor.Service;

import com.example.Doctor.Model.AuthenticationToken;
import com.example.Doctor.Model.Patient;

public interface IAuthService {

    void saveToken(AuthenticationToken token);
    AuthenticationToken getToken(Patient patient);
    boolean authenticate(String userEmail, String token);
}
