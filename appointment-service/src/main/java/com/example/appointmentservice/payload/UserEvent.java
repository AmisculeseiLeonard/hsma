package com.example.appointmentservice.payload;

import com.example.appointmentservice.model.BusinessHours;
import com.example.appointmentservice.model.Service;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserEvent {
    private EventType eventType;
    private String userId;
    private String username;
    private String email;
    private boolean isHealthcareSpecialist;
    private BusinessHours businessHours;
    private List<Service> services;
}
