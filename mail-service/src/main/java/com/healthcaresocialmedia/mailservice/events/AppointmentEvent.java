package com.healthcaresocialmedia.mailservice.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentEvent {
    private EventType eventType;
    private Integer appointmentId;
    private String patientName;
    private String patientEmail;
    private String healthcareSpecialistName;
    private String healthcareSpecialistEmail;
    private String serviceName;
    private Double servicePrice;
    private String currency;
    private LocalDateTime appointmentFrom;
    private LocalDateTime appointmentTo;
}
