package com.healthcaresocialmedia.UserManagementService.repository;

import com.healthcaresocialmedia.UserManagementService.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    @Query(value = "{'profile.address.country' : ?0, 'profile.address.city' : ?1, 'profile.domain' : ?2}")
    List<User> findHealthcareSpecialist(String country, String city, String domain);

    Optional<User> findByUsername(String username);
}
