/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Controller;

import com.wwanat.CryptoWorld.HttpModels.NotificationRequest;
import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.Notification;
import com.wwanat.CryptoWorld.Service.NotificationService;
import com.wwanat.CryptoWorld.Service.UserService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Wiktor
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    final private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(method = RequestMethod.GET, value = "/myCryptocurrencies")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity getUserFavouriteCryptocurrenciesList() {
        logger.info("Calling /api/myCryptocurrencies GET method", CryptocurrencyController.class);
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Cryptocurrency> favourite = null;
            favourite = userService.getUserFavouriteCryptocurrencies(username);
            return new ResponseEntity(favourite, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cryptocurrency/{name}")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity addCryptocurrencyToUserFavouriteList(@PathVariable String name) {
        logger.info("Calling /api/cryptocurrency/" + name + " POST method", CryptocurrencyController.class);
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            userService.addCryptocurrencyToFavourite(username, name);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/myCryptocurrencies/remove/{name}")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity removeCryptocurrencyFromUserFavouriteList(@PathVariable String name) {
        logger.info("Calling /api/cryptocurrency/" + name + "/removeFavourite POST method", CryptocurrencyController.class);
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            userService.removeCryptocurrencyFromFavourite(username, name);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/notifications/myNotifications")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity getUserNotifications() {
        logger.info("Calling /api/myNotifications GET method", CryptocurrencyController.class);
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Notification> userNotifications = userService.getUserNotifications(username);
            return new ResponseEntity(userNotifications, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/notifications/myNotifications/{id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity removeUserNotification(@PathVariable String id) {
        logger.info("Calling /api/myNotifications" + id + " GET method", CryptocurrencyController.class);
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            userService.removeNotificationFromUserByID(username, id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/notifications")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity createUserNotification(@RequestBody NotificationRequest notificationRequest) {
        logger.info("Calling /api/notifications POST method -creating notification", CryptocurrencyController.class);
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            userService.addNotificationToUser(username, notificationRequest);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/notifications/{id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity updateUserNotification(@RequestBody Notification notification) {
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
