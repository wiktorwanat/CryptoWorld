/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Security.Service;

import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wiktor
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
     final private static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u=userService.getByUsername(username);
        if(u==null){
            logger.warn("User with given username not found", UserDetailsServiceImpl.class);
            throw new UsernameNotFoundException("User with given username not found");
        }else{
            logger.info("User with given username found", UserDetailsServiceImpl.class);
            return UserDetailsImpl.build(u);
        }
    }
    
    
    
}
