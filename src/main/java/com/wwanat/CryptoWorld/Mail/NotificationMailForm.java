package com.wwanat.CryptoWorld.Mail;

import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Model.Notification;

public class NotificationMailForm {

    public static final String notificationMailTitle = "Notification from CryptoWorld!";

    private static String notificationMailContent = "<h1 style='text-align: center;'>CryptoWorld Notification</h1>" +
            "<h3>H1 %s, Actual price of %s just went %s  the %d</h3>" +
            "<h3>Actual price of %s just went %s  the %d</h3>" +
            "<h4 style='text-align: center;'> %s</h4>" +
            "<h4 style='text-align: center;'>Actual Price: %d</h4>" +
            "<h4 style='text-align: center;'>MarketCap: %d</h4>" +
            "<h4 style='text-align: center;'>1h change: %s</h4>";

    public static String getNotificationEmailContent(User user, Notification notification) {
        if (user != null && notification != null) {
            notificationMailContent = String.format(notificationMailContent, user.getUsername(), notification.getCryptocurrency().getName(), notification.getNotificationType(), notification.getValue(), notification.getCryptocurrency().getName(), notification.getCryptocurrency().getActualPrice(), notification.getCryptocurrency().getMarketCap(), notification.getCryptocurrency().getPercent_change_1h());
        }
        return notificationMailContent;
    }
}
