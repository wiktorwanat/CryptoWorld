/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Service;

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
    
}
