package com.example.Doctor.Dao;

import com.example.Doctor.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IDoctorRepository extends JpaRepository<Doctor, Integer> {
}
