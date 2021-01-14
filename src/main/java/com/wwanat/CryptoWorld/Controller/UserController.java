/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Controller;

import com.wwanat.CryptoWorld.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Wiktor
 */
@RestController
@RequestMapping("/api")
public class UserController {
    
    final private static Logger logger = LoggerFactory.getLogger(CryptocurrencyController.class);

    
    @Autowired
    private UserService userService;
    
    //@Autowired
    //private 
    
}
