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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Wiktor
 */
@RestController
@RequestMapping("/api")
public class UserController {
    
    final private static Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(method=RequestMethod.GET,value="/cryptocurrency/my")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity getAllUserFavouriteCryptocurrencies(@CurrentSecurityContext String username){
        logger.info("Calling /api/cryptocurrency/create POST method",CryptocurrencyController.class);
        try{
            return new ResponseEntity(userService.getUserFavouriteCryptocurrencies(username),HttpStatus.OK);
        }catch(Exception e){
            logger.error("Server Error "+ e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }
    
    @RequestMapping(method=RequestMethod.POST,value="/cryptocurrency/{name}")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public ResponseEntity addCRyptocurrencyToUserFavouriteLIst(@CurrentSecurityContext String username,@PathVariable String name){
        logger.info("Calling /api/cryptocurrency/create POST method",CryptocurrencyController.class);
        try{
            userService.addCryptocurrencyToFavourite(username,name);
            return new ResponseEntity(HttpStatus.OK);
        }catch(Exception e){
            logger.error("Server Error "+ e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }
}
