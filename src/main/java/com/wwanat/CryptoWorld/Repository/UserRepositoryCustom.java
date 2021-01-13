/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Repository;

import com.wwanat.CryptoWorld.Model.User;

/**
 *
 * @author Wiktor
 */
public interface UserRepositoryCustom {
    
    User findByUsername(String username);
    
    boolean userExistByUsername(String username);
    
    boolean userExistByEmail(String email);
}
