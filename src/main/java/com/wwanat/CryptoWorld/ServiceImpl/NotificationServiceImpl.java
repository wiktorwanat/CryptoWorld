package com.wwanat.CryptoWorld.ServiceImpl;

import com.wwanat.CryptoWorld.Model.Notification;
import com.wwanat.CryptoWorld.Repository.NotificationRepository;
import com.wwanat.CryptoWorld.Repository.UserRepository;
import com.wwanat.CryptoWorld.Service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class NotificationServiceImpl implements NotificationService {

    final private static Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private NotificationRepository notificationRepository;


    @Override
    public Notification create(Notification notification) {
        if(notification!=null) {
            Notification n=notificationRepository.save(notification);
            logger.info("Notification created in database",NotificationServiceImpl.class);
            return n;
        }else{
            logger.error("notification=null in create Notification method",NotificationServiceImpl.class);
            return null;
        }
    }

    @Override
    public Notification update(Notification notification) {
        if(notification!=null){
            Notification n= notificationRepository.save(notification);
            logger.info("Notification update in database",NotificationServiceImpl.class);
            return n;
        }else{
            logger.error("notification=null in update Notification method",NotificationServiceImpl.class);
            return null;
        }
    }

    @Override
    public void delete(Notification notification) {
        if (notification != null) {
            notificationRepository.delete(notification);
            logger.info("Notification deleted from database",NotificationServiceImpl.class);
        }else{
            logger.error("id=null in delete Notification method",NotificationServiceImpl.class);

        }
    }

    @Override
    public Notification getByID(String id) {
        if(id!=null){
            Notification n= notificationRepository.findById(id).get();
            logger.info("Notification collected from database",NotificationServiceImpl.class);
            return n;
        }else{
            logger.error("id=null in getByID Notification method",NotificationServiceImpl.class);
            return null;
        }
    }

    @Override
    public void deleteByID(String id) {
        if(id!=null){
             notificationRepository.deleteById(id);
        }else{
            logger.error("id=null in deleteByID Notification method",NotificationServiceImpl.class);
        }
    }

    @Override
    public List<Notification> getAllNotifications() {
        List<Notification> allNotifications=new ArrayList();
        allNotifications= notificationRepository.findAll();
        logger.info("Notifications collected from database",NotificationServiceImpl.class);
        return allNotifications;
    }
}