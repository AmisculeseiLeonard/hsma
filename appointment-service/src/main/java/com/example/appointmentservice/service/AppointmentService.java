package com.example.appointmentservice.service;

import com.example.appointmentservice.exception.InvalidAppointmentRequestException;
import com.example.appointmentservice.exception.MessagingException;
import com.example.appointmentservice.exception.ResourceNotFoundException;
import com.example.appointmentservice.messaging.AppointmentEventSender;
import com.example.appointmentservice.model.Appointment;
import com.example.appointmentservice.model.BusinessHours;
import com.example.appointmentservice.model.User;
import com.example.appointmentservice.payload.AppointmentRequest;
import com.example.appointmentservice.payload.EventType;
import com.example.appointmentservice.repository.AppointmentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final AppointmentEventSender appointmentEventSender;

    public AppointmentService(AppointmentRepository appointmentRepository, UserService userService, AppointmentEventSender appointmentEventSender) {
        this.appointmentRepository = appointmentRepository;
        this.userService = userService;
        this.appointmentEventSender = appointmentEventSender;
    }

    public Appointment createAppointment(AppointmentRequest appointmentRequest) {
        User patient = userService.findUser(appointmentRequest.getPatientId());
        User healthcareSpecialist = userService.findUser(appointmentRequest.getHealthcareSpecialistId());
        com.example.appointmentservice.model.Service service = healthcareSpecialist.getServices().stream()
                .filter(s -> s.getServiceId() == appointmentRequest.getServiceId())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Invalid service ID"));

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .healthcareSpecialist(healthcareSpecialist)
                .service(service)
                .appointmentFrom(appointmentRequest.getAppointmentFrom())
                .appointmentTo(appointmentRequest.getAppointmentTo())
                .isAccepted(false)
                .build();

        boolean isAppointmentRequestValid = validateAppointment(healthcareSpecialist.getBusinessHours(), appointmentRequest.getAppointmentFrom(), appointmentRequest.getAppointmentTo(), healthcareSpecialist.getUserid());
        if(isAppointmentRequestValid) {
            Appointment savedAppointment = appointmentRepository.save(appointment);
            try {
                appointmentEventSender.sendAppointmentEvent(appointment, EventType.APPOINTMENT_REQUEST);
            } catch (JsonProcessingException e) {
                throw new MessagingException("Error occurred while trying to send message");
            }
            return savedAppointment;
        }else{
            throw new InvalidAppointmentRequestException("The appointment request is invalid");
        }
    }

    private boolean validateAppointment(BusinessHours businessHours, LocalDateTime from, LocalDateTime to, String healthcareSpecialistId) {
        boolean fromBiggerThanBusinessHourStart = LocalTime.from(from).compareTo(businessHours.getFrom()) >= 0;
        boolean toBiggerThanBusinessHourStart = LocalTime.from(to).compareTo(businessHours.getFrom()) > 0;
        boolean fromLowerThanBusinessHourEnd = LocalTime.from(from).compareTo(businessHours.getTo()) < 0;
        boolean toLowerThanBusinessHourEnd = LocalTime.from(to).compareTo(businessHours.getTo()) <= 0;

        boolean isSameDay = LocalDate.from(from).equals(LocalDate.from(to));

        List<Appointment> appointmentList = appointmentRepository.findAppointmentsByUserIdAndDate(healthcareSpecialistId, LocalDate.from(from));

        boolean isOverlap = appointmentList.stream().anyMatch(appointment ->
                ((from.isAfter(appointment.getAppointmentFrom()) || from.isEqual(appointment.getAppointmentFrom())) &&
                from.isBefore(appointment.getAppointmentTo())) ||
                (to.isAfter(appointment.getAppointmentFrom()) &&
                (to.isBefore(appointment.getAppointmentTo()) || to.isEqual(appointment.getAppointmentTo()))));
        System.out.println("isSameDay" + isSameDay + " isOverlap" + isOverlap
                + " fromBiggerThanBusinessHourStart" + fromBiggerThanBusinessHourStart
                + " toBiggerThanBusinessHourStart" + toBiggerThanBusinessHourStart
                + " fromLowerThanBusinessHourEnd" + fromLowerThanBusinessHourEnd
                + " toLowerThanBusinessHourEnd" + toLowerThanBusinessHourEnd
        );
        return isSameDay&&
                !isOverlap&&
                fromBiggerThanBusinessHourStart&&
                toBiggerThanBusinessHourStart&&
                fromLowerThanBusinessHourEnd&&
                toLowerThanBusinessHourEnd;
    }

    public void acceptAppointment(int appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid appointment ID"));
        appointment.setAccepted(true);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        try {
            appointmentEventSender.sendAppointmentEvent(savedAppointment, EventType.APPOINTMENT_ACCEPTED);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void refuseAppointment(int appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid appointment ID"));
        appointment.setAccepted(false);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        try {
            appointmentEventSender.sendAppointmentEvent(savedAppointment, EventType.APPOINTMENT_REFUSED);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
