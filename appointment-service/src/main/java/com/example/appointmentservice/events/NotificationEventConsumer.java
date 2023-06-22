package com.example.appointmentservice.events;


import com.example.appointmentservice.model.Service;
import com.example.appointmentservice.model.User;
import com.example.appointmentservice.payload.EventType;
import com.example.appointmentservice.payload.UserEvent;
import com.example.appointmentservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@Component
public class NotificationEventConsumer {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public NotificationEventConsumer(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Bean
    public Consumer<Message<String>> appointmentConsumer() {
        return message -> {
            log.info("Received new message from Kafka topic");
            try {
                if(Objects.equals(message.getHeaders().get("type"), EventType.USER_ACCOUNT_CONFIRMED.name())){
                    UserEvent userevent = objectMapper.readValue(message.getPayload(), UserEvent.class);
                    userService.createUser(mapToUser(userevent));
                }
            }catch (Exception e) {
                log.error("Error occurred while trying to read the message", e);
            }
        };
    }

    private User mapToUser(UserEvent userEvent) {
        User user = new User();
        user.setUserid(userEvent.getUserId());
        user.setUsername(userEvent.getUsername());
        user.setEmail(userEvent.getEmail());
        user.setHealthcareSpecialist(userEvent.isHealthcareSpecialist());
        if(userEvent.isHealthcareSpecialist()) {
            user.setBusinessHours(userEvent.getBusinessHours());
            List<Service> services = userEvent.getServices().stream()
                    .peek(service -> service.setUser(user))
                    .toList();
            user.setServices(services);
        }
        System.out.println(user);
        return user;
    }
}
