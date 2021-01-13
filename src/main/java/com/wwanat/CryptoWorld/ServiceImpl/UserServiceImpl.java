/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.ServiceImpl;

import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Repository.UserRepository;
import com.wwanat.CryptoWorld.Service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
                logger.info("given user id to remove is nullable", UserServiceImpl.class);
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


    
}
