/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Controller;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Service.CryptocurrencyService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Wiktor
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CryptocurrencyController {
    
    final private static Logger logger = LoggerFactory.getLogger(CryptocurrencyController.class);

    @Autowired
    private CryptocurrencyService cryptocurrencyService;
    
    @RequestMapping(method=RequestMethod.GET,value="/cryptocurrency")
    @ResponseBody
    public ResponseEntity allCryptocurriencies(){
        
        logger.info("Calling /api/cryptocurrency GET method",CryptocurrencyController.class); 
        List<Cryptocurrency> allCryptocurrencies=new ArrayList<Cryptocurrency>();
        try{
            allCryptocurrencies=cryptocurrencyService.getAll();
            return new ResponseEntity(allCryptocurrencies,HttpStatus.OK);
        }catch(Exception e){
            logger.error("Server Error "+ e, CryptocurrencyController.class);
            return new ResponseEntity(allCryptocurrencies,HttpStatus.valueOf(404));
        }
    }
    
    @RequestMapping(method=RequestMethod.GET,value="/cryptocurrency/{name}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity getCryptocurrencyByName(@PathVariable("name") String name){
        logger.info("Calling /api/cryptocurrency/"+name+" GET method",CryptocurrencyController.class);
        Cryptocurrency cryptocurrency=null;
        try{
            cryptocurrency=cryptocurrencyService.getByName(name);
            return new ResponseEntity(cryptocurrency,HttpStatus.OK);
        }catch(Exception e){
            logger.error("Server Error "+ e, CryptocurrencyController.class);
            return new ResponseEntity(cryptocurrency,HttpStatus.valueOf(404));
        }
    }
    
    @RequestMapping(method=RequestMethod.DELETE,value="/cryptocurrency/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity deleteCryptocurrencyByName(@PathVariable("name") String name){
        logger.info("Calling /api/cryptocurrency/"+name+" DELETE method",CryptocurrencyController.class);
        Cryptocurrency cryptocurrency=null;
        try{
            cryptocurrencyService.removeCryptocurrencyByName(name);
            return new ResponseEntity(HttpStatus.OK);
        }catch(Exception e){
            logger.error("Server Error "+ e, CryptocurrencyController.class);
            return new ResponseEntity(cryptocurrency,HttpStatus.valueOf(404));
        }
    }
    
    @RequestMapping(method=RequestMethod.POST,value="/cryptocurrency/create")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity createCryptocurrency(@RequestBody Cryptocurrency cryptocurrency){
        logger.info("Calling /api/cryptocurrency/create POST method",CryptocurrencyController.class);
        try{
            cryptocurrencyService.createCryptocurrency(cryptocurrency);
            return new ResponseEntity(cryptocurrencyService.createCryptocurrency(cryptocurrency),HttpStatus.OK);
        }catch(Exception e){
            logger.error("Server Error "+ e, CryptocurrencyController.class);
            return new ResponseEntity(HttpStatus.valueOf(404));
        }
    }
    
}
