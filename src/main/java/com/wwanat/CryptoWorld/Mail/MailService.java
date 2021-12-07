/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Mail;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Reports.Report;
import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Model.Notification;

import javax.mail.MessagingException;

/**
 * @author Wiktor
 */

public interface MailService {

    void sendRegistrationMail(User user) throws MessagingException;

    void sendNotificationMail(User user, Notification notification) throws MessagingException;

    void sendReportMail(User user, Cryptocurrency cryptocurrency, Report report) throws MessagingException;
}
