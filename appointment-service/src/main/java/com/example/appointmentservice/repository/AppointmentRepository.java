package com.example.appointmentservice.repository;

import com.example.appointmentservice.model.Appointment;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(nativeQuery = true, value = "select * from appointment where healthcare_specialist_id = ?#{[0]} and cast(appointment_from as date)= ?#{[1]}")
    List<Appointment> findAppointmentsByUserIdAndDate(String healthcareSpecialistId, LocalDate date);
}
