/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Mail;

import com.wwanat.CryptoWorld.Model.User;

/**
 * @author Wiktor
 */
public class RegistrationEmailForm {

    public static final String registrationEmailTitle = "Welcome in CryptoWorld!";

    private static String restriationEmailContent = "Thank you for registering on our website %s .";

    public static String getRestriationEmailContent(User user) {
        if (user != null) {
            restriationEmailContent = String.format(restriationEmailContent, user.getUsername());
        }
        return restriationEmailContent;
    }

}
