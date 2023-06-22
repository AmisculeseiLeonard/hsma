package com.healthcaresocialmedia.relationshipservice.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcaresocialmedia.relationshipservice.model.User;
import com.healthcaresocialmedia.relationshipservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

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
    public Consumer<Message<String>> graphConsumer() {
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
        user.setId(userEvent.getUserId());
        user.setUsername(userEvent.getUsername());
        user.setEmail(userEvent.getEmail());
        return user;
    }
}
