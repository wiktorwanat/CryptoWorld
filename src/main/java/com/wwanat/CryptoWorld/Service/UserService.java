/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Service;

import com.wwanat.CryptoWorld.HttpModels.NotificationRequest;
import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.Notification;
import com.wwanat.CryptoWorld.Model.User;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Wiktor
 */
public interface UserService{
    
    User findByUsername(String username);
    
    boolean userExistByUsername(String username);
    
    boolean userExistByEmail(String email);
    
    User createUser(User user);
    
    Optional<User> getById(String id);
    
    User updateUser(User user);
    
    void removeUser(String id);
    
    List<User> getUsers();
    
    List<Cryptocurrency> getUserFavouriteCryptocurrencies(String username);
    
    void addCryptocurrencyToFavourite(String username,String newFavouriteCryptocurrencyName);
    
    void removeCryptocurrencyFromFavourite(String username,String newFavouriteCryptocurrencyName);

    List<Notification> getUserNotifications(String username);

    void addNotificationToUser(String username, NotificationRequest notificationRequest);

    void removeNotificationFromUser(String username,Notification notification);


    void removeNotificationFromUserByID(String username,String notificationId);
}
