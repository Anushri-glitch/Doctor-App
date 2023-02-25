package com.example.Doctor.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Doctor_table")
public class Doctor {
    @Id
    @Column(name="Doctor_Id")
    private Integer doctorId;

    @Column(name="Doctor_name")
    private String doctorName;

    @Column(name="Doctor_Exp")
    private String Experience;

    @Column(name="Doctor_Spec")
    private String Specialisation;
}
