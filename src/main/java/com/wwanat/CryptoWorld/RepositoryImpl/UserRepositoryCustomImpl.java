/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.RepositoryImpl;

import com.wwanat.CryptoWorld.Model.Notification;
import com.wwanat.CryptoWorld.Model.Role;
import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Repository.UserRepositoryCustom;
import java.util.List;
import static org.apache.tomcat.jni.Lock.name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 *
 * @author Wiktor
 */
public class UserRepositoryCustomImpl implements UserRepositoryCustom{
    
    final private static Logger logger = LoggerFactory.getLogger(RoleRepositoryCustomImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public User findByUsername(String username) {
        User u=null;
        final Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        if(username!=null){
            List<User> list=mongoTemplate.find(query, User.class);
            if(list!=null && list.size()>0){
                if(list.size()==1){
                    u=list.get(0);
                }else{
                    logger.error("Mistakes in database ",UserRepositoryCustomImpl.class);
                } 
            }
        }
        return u;
    }

    @Override
    public boolean userExistByUsername(String username){
        User u=null;
       u=findByUsername(username);
        if(u!=null){
            logger.info("User with usename "+username+" found in db",UserRepositoryCustomImpl.class);
            return true;
        }else{
            logger.info("User with usename "+username+" doesn't exist in db",UserRepositoryCustomImpl.class);
            return false;
        }
    }

    @Override
    public boolean userExistByEmail(String email) {
        User u=null;
        final Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        if(email!=null){
            List<User> list=mongoTemplate.find(query, User.class);
            if(list!=null && list.size()>0){
                if(list.size()==1){
                    u=list.get(0);
                    logger.info("User with email "+email+" found in db",UserRepositoryCustomImpl.class);
                    return true;
                }else if(list.size()>1){
                    logger.error("Mistakes in database ",CryptocurrencyRepositoryCustomImpl.class);
                }else{
                    logger.info("User with email "+email+" doesn't exist in db",UserRepositoryCustomImpl.class);
                }
            }
        }
        return false;
    }

    @Override
    public void deleteUserDependencies(String id) {
        if(id!=null){
            final Query query = new Query();
            query.addCriteria(Criteria.where("notoficationOwner").is(id));
            mongoTemplate.findAllAndRemove(query,Notification.class);
        }

    }
}
