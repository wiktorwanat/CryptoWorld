/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.ServiceImpl;

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
    public User findByUsername(String username) {
        logger.info("findByUsername with username "+username , UserServiceImpl.class);
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean userExistByUsername(String username) {
        logger.info("userExistByUsername with username "+username , UserServiceImpl.class);
        return userRepository.userExistByUsername(username);
    }

    @Override
    public boolean userExistByEmail(String email) {
        logger.info("userExistByEmail with email "+email , UserServiceImpl.class);
        return userRepository.userExistByEmail(email);
    }

    @Override
    public User createUser(User user) {
        logger.info("Creating new user "+user.toString() , UserServiceImpl.class);
        try{
            mailService.sendRegistrationMail(user);
        }catch( MessagingException e){
            logger.error("RegistrationMail failed to send -method createUser", UserServiceImpl.class);
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getById(String id) {
        logger.info("getById with id "+id, UserServiceImpl.class);
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User user) {
        logger.info("Update user "+user.toString() , UserServiceImpl.class);
        return userRepository.save(user);
    }

    @Override
    public void removeUser(String id) {
            if(id==null){
                logger.error("given user id to remove is nullable", UserServiceImpl.class);
            }else{
                userRepository.deleteById(id);
                logger.info("user removing succes with ocject id "+id, UserServiceImpl.class);
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
    public List<Cryptocurrency> getUserFavouriteCryptocurrencies(String username) {
        List<Cryptocurrency> userFavouriteCryptocurrencyList=new ArrayList();
        try{
            userFavouriteCryptocurrencyList=userRepository.findByUsername(username).getUserCryptocurrency();
            logger.info("User "+username +" favourite Cryptocurrency list collected", UserServiceImpl.class);
        }catch(Exception e){
            logger.error("Exception thrown in addCryptocurrencyToFavourite method ", UserServiceImpl.class);
        }
        return userFavouriteCryptocurrencyList;
    }
    
    @Override
    public void addCryptocurrencyToFavourite(String username,String newFavouriteCryptocurrencyName){
        try{
            User user=userRepository.findByUsername(username);
            if(user!=null){
                Cryptocurrency newFavouriteCryptocurrency=cryptocurrencyService.getByName(newFavouriteCryptocurrencyName);
                if(newFavouriteCryptocurrency!=null){
                    List<Cryptocurrency> cryptoList=user.getUserCryptocurrency();
                    boolean exist=false;
                    if(cryptoList!=null){
                        if(cryptoList.size()>0){
                            for(Cryptocurrency c: cryptoList){
                                if(c!=null){
                                    if(c.getName().contains(newFavouriteCryptocurrency.getName())){exist=true;}
                                }
                            }
                        }
                        if(exist==false){
                            user.getUserCryptocurrency().add(newFavouriteCryptocurrency);
                            updateUser(user);
                            logger.info("Cryptocurrency "+newFavouriteCryptocurrencyName+" added to "+username+" favourite list", UserServiceImpl.class);
                        }else{
                            logger.info("Cryptocurrency "+newFavouriteCryptocurrencyName+" is already in "+username+" favourite Cryptoucrrency list", UserServiceImpl.class);
                        }
                    }
                }else{
                    logger.warn("Cryptocurrency "+newFavouriteCryptocurrencyName+" not found in db ", UserServiceImpl.class);
                }
            }else{
                logger.warn("given user with username "+username+" not found" , UserServiceImpl.class);
            }
        }catch(Exception e){
            logger.error("Exception thrown in addCryptocurrencyToFavourite method ", UserServiceImpl.class);
        }
    }

    @Override
    public void removeCryptocurrencyFromFavourite(String username, String newFavouriteCryptocurrencyName) {
        try{
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
        }catch(Exception e){
            logger.error("Exception thrown in removeCryptocurrencyFromFavourite method ", UserServiceImpl.class);
        }
    }

    @Override
    public List<Notification> getUserNotifications(String username) {
        List<Notification> notifications=new ArrayList();
        try{
            User user=userRepository.findByUsername(username);
            notifications=user.getUserNotification();
        }catch(Exception e){
            logger.error("Exception thrown in getUserNotifications method ", UserServiceImpl.class);
        }
        return notifications;
    }

    @Override
    public void addNotificationToUser(String username, NotificationRequest notificationRequest) {
        try{
            User user=userRepository.findByUsername(username);
            Notification createdNotification=notificationService.createNotificationFromRequest(notificationRequest);
            if(user!=null) {
                user.addNotification(createdNotification);
                userRepository.save(user);
            }
            logger.info("Notification successfully added from user data",UserServiceImpl.class);
        }catch(Exception e){
            logger.error("Exception thrown in addNotificationToUser method ", UserServiceImpl.class);
        }
    }

    @Override
    public void removeNotificationFromUser(String username, Notification notification) {
        try{
            User user=userRepository.findByUsername(username);
            if(user!=null && user.getUserNotification().contains(notification)) {
                user.removeNotification(notification);
                userRepository.save(user);
                notificationService.delete(notification);
            }
            logger.info("Notification successfully removed from user data",UserServiceImpl.class);
        }catch(Exception e){
            logger.error("Exception thrown in removeNotificationFromUser method ", UserServiceImpl.class);
        }
    }


    @Override
    public void removeNotificationFromUserByID(String username, String notificationId) {
        try{
            User user=userRepository.findByUsername(username);
            Notification n=notificationService.getByID(notificationId);
            if(user!=null && user.getUserNotification().contains(n)) {
                user.removeNotification(n);
                userRepository.save(user);
                notificationService.deleteByID(notificationId);
            }
            logger.info("Notification successfully removed from user data",UserServiceImpl.class);
        }catch(Exception e){
            logger.error("Exception thrown in removeNotificationFromUser method ", UserServiceImpl.class);
        }
    }
}
