package com.wwanat.CryptoWorld.Service;

import org.springframework.stereotype.Service;
import com.wwanat.CryptoWorld.Model.Notification;

@Service
public interface NotificationService {

    Notification create(Notification notification);

    Notification update(Notification notification);

    void delete(Notification notification);

}