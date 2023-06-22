package com.healthcaresocialmedia.mailservice.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcaresocialmedia.mailservice.exception.UnknownUserEventType;
import com.healthcaresocialmedia.mailservice.model.MailContent;
import com.healthcaresocialmedia.mailservice.model.MailTemplate;
import com.healthcaresocialmedia.mailservice.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@Component
public class NotificationEventConsumer {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    public NotificationEventConsumer(EmailService emailService, ObjectMapper objectMapper) {
        this.emailService = emailService;
        this.objectMapper = objectMapper;
    }

//    @Bean
//    public Consumer<UserEvent> userEventConsumer() {
//        return userEvent -> {
//            log.info("Received new message from Kafka topic");
////            if(userEvent.getUserEventType().equals(UserEventType.CREATED)){
////                emailService.sendEmail(userEvent.getEmail());
////            }
//            MailContent mailContent = MailTemplate.getMailTemplate(userEvent);
//            emailService.sendEmail(mailContent);
//        };
//    }

    @Bean
    public Consumer<Message<String>> mailConsumer() {
        return message -> {
            log.info("Received new message from Kafka topic" + message.getPayload() + "header " + message.getHeaders().get("type"));
            try{
                var messageType = message.getHeaders().get("type");
                boolean isUserEvent = Objects.equals(messageType, EventType.USER_CREATED.name()) ||
                                Objects.equals(messageType, EventType.USER_ACCOUNT_CONFIRMED.name()) ||
                                Objects.equals(messageType, EventType.USER_UPDATED.name());
                boolean isAppointmentEvent = Objects.equals(messageType, EventType.APPOINTMENT_REQUEST.name()) ||
                        Objects.equals(messageType, EventType.APPOINTMENT_ACCEPTED.name()) ||
                        Objects.equals(messageType, EventType.APPOINTMENT_REFUSED.name());

                if(isUserEvent) {
                    UserEvent userEvent = objectMapper.readValue(message.getPayload(), UserEvent.class);
                    MailContent mailContent = MailTemplate.getMailTemplate(userEvent);
                    emailService.sendEmail(mailContent);
                }else if (isAppointmentEvent) {
                    AppointmentEvent appointmentEvent = objectMapper.readValue(message.getPayload(), AppointmentEvent.class);
                    MailContent mailContent = MailTemplate.getMailTemplate(appointmentEvent);
                    emailService.sendEmail(mailContent);
                }else{
                    throw new UnknownUserEventType(messageType + "is not a recognized event type");
                }
            }catch (Exception e){
                log.error("Error occurred while trying to read the message", e);
            }
        };
    }
}
