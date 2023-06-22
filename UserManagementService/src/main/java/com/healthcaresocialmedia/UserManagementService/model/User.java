package com.healthcaresocialmedia.UserManagementService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @Email
    private String email;
    private boolean activated;
    @CreatedDate
    private Instant createdAt;
    @NotNull
    private boolean isHealthcareSpecialist;
    private Profile profile;

    public User () {
        this.createdAt = Instant.now();
    }
}
