package com.example.Doctor.Model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Patient_Table")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    private String patientFirstName;
    private String patientLastName;
    @Column(nullable = false, unique = true)
    private String patientEmail;
    @Column(nullable = false)
    private String patientPassword;
    private String patientContact;

    public Patient(String patientFirstName, String patientLastName, String patientEmail, String patientPassword, String patientContact) {
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.patientEmail = patientEmail;
        this.patientPassword = patientPassword;
        this.patientContact = patientContact;
    }

    @OneToOne(mappedBy = "patient")
    private Appointment appointments;

}
