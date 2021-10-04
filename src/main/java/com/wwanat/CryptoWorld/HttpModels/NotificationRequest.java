package com.wwanat.CryptoWorld.HttpModels;

import com.wwanat.CryptoWorld.Model.Types.NotificationType;

public class NotificationRequest {

    private Double notificationValue;
    private String notificationCryptocurrencyName;
    private String notificationOwner;
    private NotificationType notificationType;

    public NotificationRequest() {
    }

    public NotificationRequest(Double notificationValue, String notificationCryptocurrencyName, String notificationOwner, NotificationType notificationType) {
        this.notificationValue = notificationValue;
        this.notificationCryptocurrencyName = notificationCryptocurrencyName;
        this.notificationOwner = notificationOwner;
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

    public String getNotificationOwner() {
        return notificationOwner;
    }

    public void setNotificationOwner(String notificationOwner) {
        this.notificationOwner = notificationOwner;
    }
}
