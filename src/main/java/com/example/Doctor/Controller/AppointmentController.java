package com.example.Doctor.Controller;
import com.example.Doctor.Model.Appointment;
import com.example.Doctor.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @PostMapping()
    public void bookAppointment(@RequestBody Appointment appointment)
    {
        appointmentService.bookAppointment(appointment);
    }



}
