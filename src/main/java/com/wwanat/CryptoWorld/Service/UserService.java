/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Service;

import com.wwanat.CryptoWorld.Exception.EntityNotFoundException;
import com.wwanat.CryptoWorld.HttpModels.NotificationRequest;
import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.Notification;
import com.wwanat.CryptoWorld.Model.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Wiktor
 */
public interface UserService {

    User getByUsername(String username) throws Exception;

    boolean userExistByUsername(String username) throws Exception;

    boolean userExistByEmail(String email) throws Exception;

    User createUser(User user) throws Exception;

    User getById(String id) throws Exception;

    User updateUser(User user) throws Exception;

    void removeUser(String id) throws Exception;

    List<User> getUsers();

    List<Cryptocurrency> getUserFavouriteCryptocurrencies(String username) throws Exception;

    void addCryptocurrencyToFavourite(String username, String newFavouriteCryptocurrencyName) throws Exception;

    void removeCryptocurrencyFromFavourite(String username, String newFavouriteCryptocurrencyName) throws Exception;

}
