package com.example.appointmentservice.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentRequest {
    private String patientId;
    private String healthcareSpecialistId;
    private int serviceId;
    private LocalDateTime appointmentFrom;
    private LocalDateTime appointmentTo;
}
