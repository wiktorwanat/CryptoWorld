/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.RepositoryImpl;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.EnumRole;
import com.wwanat.CryptoWorld.Model.Role;
import com.wwanat.CryptoWorld.Repository.RoleRepositoryCustom;
import java.util.List;
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

public class RoleRepositoryCustomImpl implements RoleRepositoryCustom{
    
    final private static Logger logger = LoggerFactory.getLogger(RoleRepositoryCustomImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Role findByRoleName(EnumRole name) {
        Role r=null;
        final Query query = new Query();
        query.addCriteria(Criteria.where("roleName").is(name));
        if(name!=null){
            List<Role> list=mongoTemplate.find(query, Role.class);
            if(list!=null && list.size()>0){
                for(Role role: list){
                    if(role.getRoleName().equals(name)){
                        r=role;
                        logger.info("Found Role "+name,RoleRepositoryCustomImpl.class);
                    }
                }
            }
        }
        return r;
    }
    
}
