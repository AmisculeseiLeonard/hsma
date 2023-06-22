package com.example.appointmentservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    private String userid;
    private String username;
    private String email;
    private boolean isHealthcareSpecialist;
    @Embedded
    private BusinessHours businessHours;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonManagedReference
    private List<Service> services;
}
