package com.healthcaresocialmedia.mailservice.service;

import com.healthcaresocialmedia.mailservice.model.MailContent;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService{

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(MailContent mailContent) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailContent.getFrom());
        message.setTo(mailContent.getTo());
        message.setSubject(mailContent.getSubject());
        message.setText(mailContent.getText());
        emailSender.send(message);
    }
}
