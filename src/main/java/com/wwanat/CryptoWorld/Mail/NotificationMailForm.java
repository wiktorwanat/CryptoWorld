package com.wwanat.CryptoWorld.Mail;

import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Model.Notification;

import java.util.Locale;

public class NotificationMailForm {

    public static final String notificationMailTitle = "Notification from CryptoWorld!";

    private static String notificationMailContent = "<h1 style='text-align: center;'>CryptoWorld Notification</h1>" +
            "<h3>Hi %s !, Actual price of %s just went %s  the %.2f $! </h3>" +
            "<h5> %s </h4>" +
            "<h5>Actual Price: %.2f $</h5>" +
            "<h5>MarketCap: %.2f $</h5>" +
            "<h5>1h change: %.2f %%</h5>"+
            "<h5>24h change: %.2f %%</h5>"+
            "<h5>7d change: %.2f %%</h5>";

    public static String getNotificationEmailContent(User user, Notification notification) {
        if (user != null && notification != null) {
            notificationMailContent = String.format(notificationMailContent, user.getUsername(), notification.getCryptocurrency().getName(),
                    notification.getNotificationType().toString().toLowerCase(), notification.getValue(), notification.getCryptocurrency().getName(),
                    notification.getCryptocurrency().getActualPrice(), notification.getCryptocurrency().getMarketCap(), notification.getCryptocurrency().getPercent_change_1h(),
                    notification.getCryptocurrency().getPercent_change_24h(), notification.getCryptocurrency().getPercent_change_7d());
        }
        return notificationMailContent;
    }
}
