/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Controller;

import com.wwanat.CryptoWorld.HttpModels.NotificationRequest;
import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.Notification;
import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Reports.CryptocurrencyReportGenerator;
import com.wwanat.CryptoWorld.Service.CryptocurrencyService;
import com.wwanat.CryptoWorld.Service.NotificationService;
import com.wwanat.CryptoWorld.Service.UserService;

import java.util.ArrayList;
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

    @Autowired
    private CryptocurrencyReportGenerator cryptocurrencyReportGenerator;

    @Autowired
    private CryptocurrencyService cryptocurrencyService;

    @GetMapping(value = "/myCryptocurrency")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getUserFavouriteCryptocurrenciesList() {
        logger.info("Calling /api/myCryptocurrency GET method", CryptocurrencyController.class);
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

    @PostMapping(value = "/myCryptocurrency/add/{cryptocurrencyName}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity addCryptocurrencyToUserFavouriteList(@PathVariable String cryptocurrencyName) {
        logger.info("Calling /api/myCryptocurrency/add/" + cryptocurrencyName + " POST method", CryptocurrencyController.class);
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            userService.addCryptocurrencyToFavourite(username, cryptocurrencyName);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }

    @PostMapping(value = "/myCryptocurrency/remove/{cryptocurrencyName}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity removeCryptocurrencyFromUserFavouriteList(@PathVariable String cryptocurrencyName) {
        logger.info("Calling /api/myCryptocurrency/remove/" + cryptocurrencyName + " POST method", CryptocurrencyController.class);
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            userService.removeCryptocurrencyFromFavourite(username, cryptocurrencyName);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }

    @GetMapping(value = "/users/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getAllUsers(){
        try{
            return new ResponseEntity(userService.getUsers(),HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error " + e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }

    @PostMapping(value ="/cryptocurrency/{cryptocurrencyName}/report")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity orderReport(@PathVariable("cryptocurrencyName") String cryptocurrencyName) {
        logger.info("Calling /api/cryptocurrency/report POST method", CryptocurrencyController.class);
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.getByUsername(username);
            Cryptocurrency cryptocurrency = cryptocurrencyService.getByName(cryptocurrencyName);
            cryptocurrencyReportGenerator.generateCryptocurrencyReport(user, cryptocurrency);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Server Error while generating cryptocurrency report" + e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }

}
