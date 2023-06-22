package com.example.appointmentservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Appointment {
    @Id @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;
    @ManyToOne
    @JoinColumn(name = "healthcareSpecialist_id")
    private User healthcareSpecialist;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;
    private LocalDateTime appointmentFrom;
    private LocalDateTime appointmentTo;
    private boolean isAccepted;
}
