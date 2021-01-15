/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.RepositoryImpl;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Repository.CryptocurrencyRepositoryCustom;
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
public class CryptocurrencyRepositoryCustomImpl implements CryptocurrencyRepositoryCustom{
    
    final private static Logger logger = LoggerFactory.getLogger(CryptocurrencyRepositoryCustomImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Cryptocurrency findCryptocurrencyByName(String name) {
        Cryptocurrency c=null;
        final Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        if(name!=null){
            List<Cryptocurrency> list=mongoTemplate.find(query, Cryptocurrency.class);
            if(list.size()==1){
                logger.info("Cryptocurrency with given name "+name+" exists",CryptocurrencyRepositoryCustomImpl.class);
                c=list.get(0);
            }else{
                logger.error("missing Cryptocurrency with given name",CryptocurrencyRepositoryCustomImpl.class);
            }
        }
        return c;
    }
    
    
}