/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Controller;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Repository.CryptocurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Wiktor
 */
@RestController
public class CryptocurrencyController {
    
    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;
    
    @RequestMapping(method=RequestMethod.GET,value="/cryptocurrency")
    public Iterable<Cryptocurrency> allCryptocurriencies(){
        return cryptocurrencyRepository.findAll();
    }
    
}
