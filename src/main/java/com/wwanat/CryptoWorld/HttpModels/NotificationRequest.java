package com.wwanat.CryptoWorld.HttpModels;

import com.wwanat.CryptoWorld.Model.NotificationType;

public class NotificationRequest {
    private Double notificationValue;
    private String notificationCryptocurrencyName;
    private NotificationType notificationType;

    public NotificationRequest() {
    }

    public NotificationRequest(Double notificationValue, String notificationCryptocurrencyName, NotificationType notificationType) {
        this.notificationValue = notificationValue;
        this.notificationCryptocurrencyName = notificationCryptocurrencyName;
        this.notificationType = notificationType;
    }

    public Double getNotificationValue() {
        return notificationValue;
    }

    public void setNotificationValue(Double notificationValue) {
        this.notificationValue = notificationValue;
    }

    public String getNotificationCryptocurrencyName() {
        return notificationCryptocurrencyName;
    }

    public void setNotificationCryptocurrencyName(String notificationCryptocurrencyName) {
        this.notificationCryptocurrencyName = notificationCryptocurrencyName;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
