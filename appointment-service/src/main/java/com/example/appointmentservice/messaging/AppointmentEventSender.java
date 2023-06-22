package com.example.appointmentservice.messaging;

import com.example.appointmentservice.model.Appointment;
import com.example.appointmentservice.payload.AppointmentEvent;
import com.example.appointmentservice.payload.EventType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Configuration
public class AppointmentEventSender {

    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;


    public AppointmentEventSender(StreamBridge streamBridge, ObjectMapper objectMapper) {
        this.streamBridge = streamBridge;
        this.objectMapper = objectMapper;
    }

    public void sendAppointmentEvent(Appointment appointment, EventType eventType) throws JsonProcessingException {
        String channel = "appointmentChannel";
        Message<String> message = MessageBuilder
                .withPayload(objectMapper.writeValueAsString(convertTo(appointment, eventType)))
                .setHeader("type", eventType.name())
                .build();
        streamBridge.send(channel,message);
    }

    private AppointmentEvent convertTo(Appointment appointment, EventType eventType) {
        return AppointmentEvent.builder()
                .eventType(eventType)
                .appointmentId(appointment.getId())
                .patientName(appointment.getPatient().getUsername())
                .patientEmail(appointment.getPatient().getEmail())
                .healthcareSpecialistName(appointment.getHealthcareSpecialist().getUsername())
                .healthcareSpecialistEmail(appointment.getHealthcareSpecialist().getEmail())
                .serviceName(appointment.getService().getServiceName())
                .servicePrice(appointment.getService().getPrice())
                .currency(appointment.getService().getCurrency())
                .appointmentFrom(appointment.getAppointmentFrom())
                .appointmentTo(appointment.getAppointmentTo())
                .build();
    }
}
