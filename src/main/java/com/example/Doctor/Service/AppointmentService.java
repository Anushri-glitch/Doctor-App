package com.example.Doctor.Service;
import com.example.Doctor.Dao.IAppointmentDao;
import com.example.Doctor.Model.Appointment;
import com.example.Doctor.Model.AppointmentKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    IAppointmentDao appointmentDao;

    public void bookAppointment(Appointment appointment) {
        appointmentDao.save(appointment);
    }

    public void cancelAppointment(AppointmentKey key) {
        appointmentDao.deleteById(key);
    }
}
