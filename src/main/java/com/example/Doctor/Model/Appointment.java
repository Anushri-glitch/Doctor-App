package com.example.Doctor.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Appointment.class,property = "id")
public class Appointment {
    @Id
    @EmbeddedId
    private AppointmentKey id;

    @ManyToOne
    @JoinColumn(name = "fk_doctor_doc_id")
    private Doctor doctor;

    @OneToOne
    private Patient patient;
}
