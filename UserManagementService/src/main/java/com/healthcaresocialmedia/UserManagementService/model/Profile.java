package com.healthcaresocialmedia.UserManagementService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private Address address;
    private BusinessHours businessHours;
    private String currentProfession;
    private List<Service> services;
    private String additionalInformation;
    private String domain;
}
