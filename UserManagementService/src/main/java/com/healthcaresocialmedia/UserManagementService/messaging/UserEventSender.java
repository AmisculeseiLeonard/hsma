package com.healthcaresocialmedia.UserManagementService.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcaresocialmedia.UserManagementService.model.BusinessHours;
import com.healthcaresocialmedia.UserManagementService.model.User;
import com.healthcaresocialmedia.UserManagementService.payload.EventType;
import com.healthcaresocialmedia.UserManagementService.payload.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@Configuration
public class UserEventSender {

    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;

    public UserEventSender(StreamBridge streamBridge, ObjectMapper objectMapper) {
        this.streamBridge = streamBridge;
        this.objectMapper = objectMapper;
    }

    public void sendUserEvent(User user, EventType eventType) throws JsonProcessingException {
        String channel = "userChannel";
        Message<String> message = MessageBuilder
                .withPayload(objectMapper.writeValueAsString(convertTo(user, eventType)))
                .setHeader("type", eventType.name())
                .build();
        streamBridge.send(channel,message);
        log.info("user event {} sent to topic {} for user {}", eventType, channel, user.getId());
    }


    private UserEvent convertTo(User user, EventType eventType) {
        BusinessHours businessHours = user.isHealthcareSpecialist() ? user.getProfile().getBusinessHours() : null;
        List<com.healthcaresocialmedia.UserManagementService.model.Service> services = user.isHealthcareSpecialist()
                ? user.getProfile().getServices() : null;
        return UserEvent.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .eventType(eventType)
                .isHealthcareSpecialist(user.isHealthcareSpecialist())
                .businessHours(businessHours)
                .services(services)
                .build();
    }
}
