/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.ServiceImpl;

import com.wwanat.CryptoWorld.Exception.EntityNotFoundException;
import com.wwanat.CryptoWorld.HttpModels.NotificationRequest;
import com.wwanat.CryptoWorld.Mail.MailService;
import com.wwanat.CryptoWorld.Mail.MailServiceImpl;
import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.Notification;
import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Repository.UserRepository;
import com.wwanat.CryptoWorld.Service.CryptocurrencyService;
import com.wwanat.CryptoWorld.Service.NotificationService;
import com.wwanat.CryptoWorld.Service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.management.NotificationFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wiktor
 */
@Service
public class UserServiceImpl implements UserService{
    
    final private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CryptocurrencyService cryptocurrencyService;

    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private MailService mailService;

    @Override
    public User getByUsername(String username) throws Exception {
        if(username!=null){
            User user=userRepository.findByUsername(username);
            if(user!=null){
                logger.info("findByUsername method with username "+username , UserServiceImpl.class);
                return user;
            }else{
                throw new EntityNotFoundException(User.class,"username",username);
            }
        }else{
            logger.info("Null pointer exception thrown in getByUsername method", UserServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean userExistByUsername(String username) throws Exception {
        if(username!=null){
            boolean exists=userRepository.userExistByUsername(username);
            logger.info("userExistByUsername method with username "+username , UserServiceImpl.class);
            return exists;
        }else{
            logger.info("Null pointer exception thrown in userExistByUsername method", UserServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean userExistByEmail(String email) throws Exception {
        logger.info("userExistByEmail with email "+email , UserServiceImpl.class);
        if(email!=null){
            boolean exists=userRepository.userExistByEmail(email);
            logger.info("userExistByEmail method with email "+email , UserServiceImpl.class);
            return exists;
        }else{
            logger.info("Null pointer exception thrown in userExistByEmail method", UserServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public User createUser(User user) throws Exception {
        logger.info("Creating new user "+user.toString() , UserServiceImpl.class);
        if(user!=null){
            User createdUser=userRepository.save(user);
            logger.info("New user created "+user.toString() , UserServiceImpl.class);
            try{
                mailService.sendRegistrationMail(user);
            }catch( MessagingException e){
                logger.error("RegistrationMail failed to send -method createUser", UserServiceImpl.class);
            }
            return createdUser;
        }else{
            logger.info("Null pointer exception thrown in createUser method", UserServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public User getById(String id) throws Exception {
        if(id!=null){
            User user=userRepository.findByUsername(id);
            if(user!=null){
                logger.info("getById method with username "+id , UserServiceImpl.class);
                return user;
            }else{
                throw new EntityNotFoundException(User.class,"id",id);
            }
        }else{
            logger.info("Null pointer exception thrown in getById method", UserServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public User updateUser(User user) throws Exception {
        if(user!=null){
            User updatedUser=userRepository.save(user);
            if(updatedUser!=null){
                logger.info("Update user "+user.toString() , UserServiceImpl.class);
                return updatedUser;
            }else{
                throw new EntityNotFoundException(User.class,"User",user.toString());
            }
        }else{
            logger.info("Null pointer exception thrown in updateUser method", UserServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void removeUser(String id) throws Exception {
            if(id!=null){
                userRepository.deleteUserDependencies(id);
                userRepository.deleteById(id);
                logger.info("User "+id+" removed from system" , UserServiceImpl.class);
            }else{
                logger.info("Null pointer exception thrown in removeUser method", UserServiceImpl.class);
                throw new IllegalArgumentException();
            }
    }

    @Override
    public List<User> getUsers() {
        List<User> users=new ArrayList<User>();
        users=userRepository.findAll();
        logger.info("All users collected", UserServiceImpl.class);
        return users;
    }

    @Override
    public List<Cryptocurrency> getUserFavouriteCryptocurrencies(String username)  throws Exception {
        List<Cryptocurrency> userFavouriteCryptocurrencyList=new ArrayList();
        if(username!=null){
            userFavouriteCryptocurrencyList=userRepository.findByUsername(username).getUserCryptocurrency();
            if(userFavouriteCryptocurrencyList!=null && !userFavouriteCryptocurrencyList.isEmpty()) {
                logger.info("User " + username + " favourite Cryptocurrency list collected", UserServiceImpl.class);
                return userFavouriteCryptocurrencyList;
            }else{
                throw new EntityNotFoundException(User.class,"Username",username);
            }
        }else{
            logger.error("Exception thrown in getUserFavouriteCryptocurrencies method ", UserServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }
    
    @Override
    public void addCryptocurrencyToFavourite(String username,String newFavouriteCryptocurrencyName) throws Exception {
        if(username!=null && newFavouriteCryptocurrencyName!=null) {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                Cryptocurrency newFavouriteCryptocurrency = cryptocurrencyService.getByName(newFavouriteCryptocurrencyName);
                if (newFavouriteCryptocurrency != null) {
                    List<Cryptocurrency> cryptoList = user.getUserCryptocurrency();
                    boolean exist = false;
                    if (cryptoList != null) {
                        if (cryptoList.size() > 0) {
                            for (Cryptocurrency c : cryptoList) {
                                if (c != null) {
                                    if (c.getName().contains(newFavouriteCryptocurrency.getName())) {
                                        exist = true;
                                    }
                                }
                            }
                        }
                        if (exist == false) {
                            user.getUserCryptocurrency().add(newFavouriteCryptocurrency);
                            updateUser(user);
                            logger.info("Cryptocurrency " + newFavouriteCryptocurrencyName + " added to " + username + " favourite list", UserServiceImpl.class);
                        } else {
                            logger.info("Cryptocurrency " + newFavouriteCryptocurrencyName + " is already in " + username + " favourite Cryptoucrrency list", UserServiceImpl.class);
                        }
                    }
                } else {
                    logger.warn("Cryptocurrency " + newFavouriteCryptocurrencyName + " not found in db ", UserServiceImpl.class);
                }
            } else {
                logger.warn("given user with username " + username + " not found", UserServiceImpl.class);
            }
        }else{
            logger.error("Exception thrown in addCryptocurrencyToFavourite method ", UserServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void removeCryptocurrencyFromFavourite(String username, String newFavouriteCryptocurrencyName)  throws Exception {
        if(username!=null && newFavouriteCryptocurrencyName!=null){
            User user=userRepository.findByUsername(username);
            if(user!=null){
                 System.out.println(user.toString());
                Cryptocurrency cryptocurrencyToRemove=cryptocurrencyService.getByName(newFavouriteCryptocurrencyName);
                if(cryptocurrencyToRemove!=null){
                    System.out.println(cryptocurrencyToRemove.toString());
                    List<Cryptocurrency> cryptoList=user.getUserCryptocurrency();
                    boolean exist=false;
                    if(cryptoList!=null){
                        for(Cryptocurrency c: cryptoList){
                            if(c!=null){
                                if(c.getName().contains(cryptocurrencyToRemove.getName()))exist=true;
                            }
                        }
                    }
                    if(exist==true){
                        user.removeFavouriteCryptocurrency(cryptocurrencyToRemove);
                        updateUser(user);
                        logger.info("Cryptocurrency "+newFavouriteCryptocurrencyName+" removed from "+username+" favourite list", UserServiceImpl.class);
                    }else{
                        logger.info("Cryptocurrency "+newFavouriteCryptocurrencyName+" is already in "+username+" favourite Cryptoucrrency list", UserServiceImpl.class);
                    }
                }else{
                    logger.warn("Cryptocurrency "+newFavouriteCryptocurrencyName+" not found in db ", UserServiceImpl.class);
                }
            }else{
                logger.warn("given user with username "+username+" not found" , UserServiceImpl.class);
            }
        }else{
            logger.error("Exception thrown in removeCryptocurrencyFromFavourite method ", UserServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

}
