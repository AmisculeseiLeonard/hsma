package com.example.appointmentservice.controller;

import com.example.appointmentservice.model.Appointment;
import com.example.appointmentservice.payload.AppointmentRequest;
import com.example.appointmentservice.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        return appointmentService.createAppointment(appointmentRequest);
    }

    @GetMapping("/accept/{appointmentId}")
    public String acceptAppointment(@PathVariable int appointmentId) {
        appointmentService.acceptAppointment(appointmentId);
        return "Appointment accepted";
    }

    @GetMapping("/refuse/{appointmentId}")
    public String refuseAppointment(@PathVariable int appointmentId) {
        appointmentService.refuseAppointment(appointmentId);
        return "Appointment refused";
    }
}
