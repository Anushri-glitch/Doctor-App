package com.example.Doctor.Dao;

import com.example.Doctor.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppointmentDao extends JpaRepository<Appointment,Integer> {
}
