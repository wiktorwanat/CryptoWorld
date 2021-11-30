/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Mail;

import com.wwanat.CryptoWorld.Model.Report;
import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Model.Notification;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author Wiktor
 */
@Service
public class MailServiceImpl implements MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    private JavaMailSender javaMailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public void sendRegistrationMail(User user) throws MessagingException {
        logger.info("Trying to send registration email", MailServiceImpl.class);
        if (user != null) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setSubject(RegistrationEmailForm.registrationEmailTitle);
            String text = RegistrationEmailForm.getRestriationEmailContent(user);
            mimeMessageHelper.setText(text);
            javaMailSender.send(mimeMessage);
            logger.info("Registration email send successfully", MailServiceImpl.class);
        } else {
            logger.error("Registration email failed to send", MailServiceImpl.class);
        }
    }

    @Override
    public void sendNotificationMail(User user, Notification notification) throws MessagingException {
        logger.info("Trying to send notification to " + user.getUsername());
        if (user != null && notification != null) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setSubject(NotificationMailForm.notificationMailTitle);
            String text = NotificationMailForm.getNotificationEmailContent(user, notification);
            mimeMessageHelper.setText(text,true);
            javaMailSender.send(mimeMessage);
            logger.info("Notification email send successfully", MailServiceImpl.class);
        } else {
            logger.error("Notification email failed to send", MailServiceImpl.class);
        }
    }

    @Override
    public void sendReportMail(User user, Report report) throws MessagingException {

    }
}
