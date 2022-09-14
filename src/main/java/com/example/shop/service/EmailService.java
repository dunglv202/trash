package com.example.shop.service;

import com.example.shop.model.Email;

import javax.mail.MessagingException;

public interface EmailService {
    void sendEmail(Email email) throws MessagingException;
}
