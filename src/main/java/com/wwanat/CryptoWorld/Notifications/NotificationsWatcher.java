package com.wwanat.CryptoWorld.Notifications;


import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.Notification;
import com.wwanat.CryptoWorld.Model.Types.NotificationType;
import com.wwanat.CryptoWorld.Mail.MailService;
import com.wwanat.CryptoWorld.Service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;

@Component
public class NotificationsWatcher {

    final private static Logger logger = LoggerFactory.getLogger(NotificationsWatcher.class);

    @Autowired
    private MailService mailService;


    @Autowired
    private NotificationService notificationService;

    @Scheduled(fixedDelayString = "${notificationWatcher.fixedDelay.in.milliseconds}", initialDelay = 50000)
    public void notificationWatcher() {
        List<Notification> notifications = null;
        notifications = notificationService.getAllNotifications();
        if (notifications != null) {
            for (Notification notification : notifications) {
                boolean notificationStatus = false;
                Cryptocurrency notificationCryptocurrency = notification.getCryptocurrency();
                if (notificationCryptocurrency != null) {
                    if (notification.getNotificationType().equals(NotificationType.OVER) && notificationCryptocurrency.getActualPrice() >= notification.getValue() || notification.getNotificationType().equals(NotificationType.BELOW) && notificationCryptocurrency.getActualPrice() <= notification.getValue()) {
                        notificationStatus = true;
                    }
                    if (notificationStatus == true) {
                        try {
                            logger.info("Notification " + notification.getId() + " activated- sending notification to user", NotificationsWatcher.class);
                            mailService.sendNotificationMail(notification.getNotifcationOwner(), notification);
                        } catch (MessagingException e) {
                            logger.error(e.getMessage(), NotificationsWatcher.class);
                        }
                    }
                }
            }
        }
    }

}
