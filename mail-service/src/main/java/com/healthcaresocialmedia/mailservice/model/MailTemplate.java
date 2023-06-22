package com.healthcaresocialmedia.mailservice.model;

import com.healthcaresocialmedia.mailservice.events.AppointmentEvent;
import com.healthcaresocialmedia.mailservice.events.UserEvent;
import com.healthcaresocialmedia.mailservice.exception.UnknownUserEventType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Configuration
public class MailTemplate {

    private static String URL_VALIDATION;
    private static String URL_ACCEPT_APPOINTMENT;
    private static String URL_REFUSE_APPOINTMENT;

    @Value("${urlValidation}")
    public void setUrlValidation(String urlValidation){
        MailTemplate.URL_VALIDATION = urlValidation;
    }
    @Value("${urlAcceptAppointment}")
    public void setUrlAcceptAppointment(String urlAcceptAppointment){
        MailTemplate.URL_ACCEPT_APPOINTMENT = urlAcceptAppointment;
    }
    @Value("${urlRefuseAppointment}")
    public void setUrlRefuseAppointment(String urlRefuseAppointment){
        MailTemplate.URL_REFUSE_APPOINTMENT = urlRefuseAppointment;
    }

    public static MailContent getMailTemplate(UserEvent userEvent) {
        switch (userEvent.getEventType()){
            case USER_CREATED -> {
                return new MailContent("healthcare.social.media.app@gmail.com", userEvent.getEmail(),
                        "Account activation", "Access the link below to activate the account: \n" + URL_VALIDATION + userEvent.getUserId());
            }
            case USER_ACCOUNT_CONFIRMED -> {
                return new MailContent("healthcare.social.media.app@gmail.com", userEvent.getEmail(),
                        "Account confirmed", "Congratulations! Your account have been confirmed.");
            }
            default -> throw new UnknownUserEventType(userEvent.getEventType() + "is not a recognized event type");
        }
    }

    public static MailContent getMailTemplate(AppointmentEvent appointmentEvent) {
        switch (appointmentEvent.getEventType()) {
            case APPOINTMENT_REQUEST -> {
                return new MailContent("healthcare.social.media.app@gmail.com", appointmentEvent.getHealthcareSpecialistEmail(),
                        "New appointment request", "Hello " + appointmentEvent.getHealthcareSpecialistName() + "!" +
                        "\n You have new appointment request from " + appointmentEvent.getPatientName() + " for " + appointmentEvent.getServiceName() + ".\n" +
                        "You can accept the appointment by accessing the following link: " + URL_ACCEPT_APPOINTMENT + appointmentEvent.getAppointmentId() + ".\n" +
                        "Or you can refuse the appointment by accessing the following link: " + URL_REFUSE_APPOINTMENT + appointmentEvent.getAppointmentId() + "."
                );
            }
            case APPOINTMENT_ACCEPTED -> {
                return new MailContent("healthcare.social.media.app@gmail.com", appointmentEvent.getPatientEmail(),
                        "New appointment request", "Hello " + appointmentEvent.getPatientName() + "!" +
                        "\n Your appointment for " + appointmentEvent.getServiceName() + " have been accepted by " + appointmentEvent.getHealthcareSpecialistName() + ". " +
                        "The price for this service will be " + appointmentEvent.getServicePrice() + " " + appointmentEvent.getCurrency());
            }
            case APPOINTMENT_REFUSED -> {
                return new MailContent("healthcare.social.media.app@gmail.com", appointmentEvent.getPatientEmail(),
                        "New appointment request", "Hello " + appointmentEvent.getPatientName() + "!" +
                        "\n Your appointment for " + appointmentEvent.getServiceName() + " have been refused by " + appointmentEvent.getHealthcareSpecialistName() + ".");
            }
            default -> throw new UnknownUserEventType(appointmentEvent.getEventType() + "is not a recognized event type");
        }
    }
}
