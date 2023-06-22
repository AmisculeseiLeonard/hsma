package com.example.appointmentservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ErrorInfo {
    private String message;
    private Integer status;
    private ZonedDateTime timestamp;
}
