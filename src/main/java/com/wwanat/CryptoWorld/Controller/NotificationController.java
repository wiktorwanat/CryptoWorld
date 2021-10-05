package com.wwanat.CryptoWorld.Controller;

import com.wwanat.CryptoWorld.HttpModels.NotificationRequest;
import com.wwanat.CryptoWorld.Model.Notification;
import com.wwanat.CryptoWorld.Service.NotificationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class NotificationController {

    final private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private NotificationService notificationService;


    @RequestMapping(method = RequestMethod.GET, value = "/notifications/myNotifications")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity getUserNotifications() {
        logger.info("Calling /api/myNotifications GET method", CryptocurrencyController.class);
        List<Notification> userNotifications = new ArrayList<>();
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            userNotifications = notificationService.getSpecificUserNotifications(username);
            return new ResponseEntity(userNotifications, HttpStatus.OK);
            //dodac obsluge odpowiedniego wyjatku illegal argument
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyController.class);
            return new ResponseEntity(userNotifications, HttpStatus.valueOf(404));
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/notifications/myNotifications/{id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity removeNotification(@PathVariable String notificationId) {
        logger.info("Calling /api/myNotifications" + notificationId + " GET method", CryptocurrencyController.class);
        try {
            notificationService.deleteByID(notificationId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/notifications")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity createNotification(@RequestBody NotificationRequest notificationRequest) {
        logger.info("Calling /api/notifications POST method -creating notification", CryptocurrencyController.class);
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            //trza to sprawdzic czy przypadkiem ten owner nie jest tam pusty w notificationrequest
            notificationService.createNotificationFromRequest(notificationRequest);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/notifications/{id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity updateNotification(@RequestBody Notification notification) {
        logger.info("Calling /api/notifications/id PUT method", CryptocurrencyController.class);
        try {
            Notification updatedNotification = notificationService.update(notification);
            return new ResponseEntity(updatedNotification, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/notifications")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity getAllNotifications() {
        logger.info("Calling /api/notifications GET method", CryptocurrencyController.class);
        try {
            List<Notification> allNotifications = notificationService.getAllNotifications();
            return new ResponseEntity(allNotifications, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }

}
