package com.wwanat.CryptoWorld.ServiceImpl;

import com.wwanat.CryptoWorld.Model.Notification;
import com.wwanat.CryptoWorld.Repository.NotificationRepository;
import com.wwanat.CryptoWorld.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;

public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification create(Notification notification) {
        if(notification!=null) {
            return notificationRepository.insert(notification);
        }else{
            return null;
        }
    }

    @Override
    public Notification update(Notification notification) {
        if(notification!=null){
            return notificationRepository.save(notification);
        }else{
            return null;
        }
    }

    @Override
    public void delete(Notification notification) {
        if (notification != null) {
            notificationRepository.delete(notification);
        }
    }
}
