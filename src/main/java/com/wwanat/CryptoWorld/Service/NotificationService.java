package com.wwanat.CryptoWorld.Service;

import com.wwanat.CryptoWorld.HttpModels.NotificationRequest;
import com.wwanat.CryptoWorld.Model.Notification;

import java.util.List;

public interface NotificationService {

    Notification getByID(String id) throws Exception;

    Notification create(Notification notification) throws Exception;

    Notification update(Notification notification) throws Exception;

    void delete(Notification notification) throws Exception;

    void deleteByID(String id) throws Exception;

    List<Notification> getAllNotifications();

    Notification createNotificationFromRequest(NotificationRequest notificationRequest) throws Exception;


}
