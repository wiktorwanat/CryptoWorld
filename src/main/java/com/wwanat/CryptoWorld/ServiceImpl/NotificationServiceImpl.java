package com.wwanat.CryptoWorld.ServiceImpl;

import com.wwanat.CryptoWorld.Exception.EntityNotFoundException;
import com.wwanat.CryptoWorld.HttpModels.NotificationRequest;
import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.Notification;
import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Repository.NotificationRepository;
import com.wwanat.CryptoWorld.Repository.UserRepository;
import com.wwanat.CryptoWorld.Service.CryptocurrencyService;
import com.wwanat.CryptoWorld.Service.NotificationService;
import com.wwanat.CryptoWorld.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    final private static Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private CryptocurrencyService cryptocurrencyService;

    @Autowired
    private UserService userService;


    @Override
    public Notification create(Notification notification) throws Exception {
        if (notification != null) {
            Notification n = notificationRepository.save(notification);
            logger.info("Notification created in database", NotificationServiceImpl.class);
            return n;
        } else {
            logger.error("Cannot create notification", NotificationServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Notification update(Notification notification) throws Exception {
        if (notification != null) {
            Notification n = notificationRepository.save(notification);
            logger.info("Notification update in database", NotificationServiceImpl.class);
            return n;
        } else {
            logger.error("Unsuccessful notification updating due to nullable id", NotificationServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void delete(Notification notification) {
        if (notification != null) {
            notificationRepository.delete(notification);
            logger.info("Notification deleted from database", NotificationServiceImpl.class);
        } else {
            logger.error("Unsuccessful notification deleting due to nullable id", NotificationServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Notification getByID(String id) {
        if (id != null) {
            Notification n = notificationRepository.findById(id).get();
            if (n != null) {
                logger.info("Notification collected from database", NotificationServiceImpl.class);
                return n;
            } else {
                throw new EntityNotFoundException(Notification.class, "id", id);
            }
        } else {
            logger.error("Cannot reach notification with nullable id", NotificationServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void deleteByID(String id) {
        if (id != null) {
            notificationRepository.deleteById(id);
            logger.info("Notification deleted from database", NotificationServiceImpl.class);
        } else {
            logger.error("Unsuccessful notification deleting due to nullable id", NotificationServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Notification> getAllNotifications() {
        List<Notification> allNotifications = new ArrayList();
        allNotifications = notificationRepository.findAll();
        logger.info("Notifications (" + allNotifications.size() + ") collected from database", NotificationServiceImpl.class);
        return allNotifications;
    }

    @Override
    public List<Notification> getSpecificUserNotifications(String notificationOwnerId) {
        List<Notification> userNotifications = new ArrayList<>();
        if(notificationOwnerId!=null) {
            for (Notification n : new ArrayList<>(getAllNotifications())) {
                if (!n.getNotifcationOwner().getId().equals(notificationOwnerId)) {
                    userNotifications.add(n);
                }
            }
            logger.info("User Notifications (" + userNotifications.size() + ") collected from database", NotificationServiceImpl.class);
            return userNotifications;

        }else{
            logger.info("User notifications cannot be collected with nullable notification owner id", NotificationServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Notification createNotificationFromRequest(NotificationRequest notificationRequest) throws Exception {
        Notification notification = new Notification();
        Cryptocurrency cryptocurrency = null;
        User notificationOwner = null;
        if (notificationRequest != null) {
            cryptocurrency = cryptocurrencyService.getByName(notificationRequest.getNotificationCryptocurrencyName());
            notificationOwner = userService.getByUsername(notificationRequest.getNotificationOwner());
            if (cryptocurrency != null && notificationOwner != null) {
                notification.setNotificationType(notificationRequest.getNotificationType());
                notification.setCryptocurrency(cryptocurrency);
                notification.setValue(notificationRequest.getNotificationValue());
                notification.setNotificationOwner(notificationOwner);
                notification = this.create(notification);
                logger.info("Notification " + notification.getId() + " created", NotificationServiceImpl.class);
                return notification;
            } else {
                throw new EntityNotFoundException(Notification.class, "notificationRequest", notificationRequest.toString());
            }
        } else {
            logger.info("Notification cannot be created due to missing cryptocurrency", NotificationServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }
}
