package com.guvi.lms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendResetEmail(
            String to,
            String resetLink) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Password Reset");
        message.setText(
                "Click the link below to reset your password:\n\n"
                        + resetLink);

        mailSender.send(message);
    }
}
