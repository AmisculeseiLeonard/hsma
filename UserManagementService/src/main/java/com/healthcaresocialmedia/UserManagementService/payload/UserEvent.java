package com.healthcaresocialmedia.UserManagementService.payload;

import com.healthcaresocialmedia.UserManagementService.model.BusinessHours;
import com.healthcaresocialmedia.UserManagementService.model.Service;
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
