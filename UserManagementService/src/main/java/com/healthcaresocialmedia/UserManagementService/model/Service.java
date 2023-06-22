package com.healthcaresocialmedia.UserManagementService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    private String serviceName;
    private Double price;
    private String description;
    private String currency;
}
