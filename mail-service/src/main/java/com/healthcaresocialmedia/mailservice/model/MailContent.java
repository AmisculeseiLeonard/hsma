package com.healthcaresocialmedia.mailservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailContent {
    private String from;
    private String to;
    private String subject;
    private String text;
}
