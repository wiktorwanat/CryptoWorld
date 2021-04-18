/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.RepositoryImpl;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.CryptocurrencyDetails;
import com.wwanat.CryptoWorld.Repository.CryptocurrencyDetailsRepositoryCustom;
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
public class CryptocurrencyDetailsRepositoryCustomImpl implements CryptocurrencyDetailsRepositoryCustom{

    final private static Logger logger = LoggerFactory.getLogger(CryptocurrencyRepositoryCustomImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Override
    public CryptocurrencyDetails findCryptocurrencyDetailsByName(String name) {
       CryptocurrencyDetails c=null;
        final Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        if(name!=null){
            List<CryptocurrencyDetails> list=mongoTemplate.find(query, CryptocurrencyDetails.class);
            if(list.size()==1){
                c=list.get(0);
            }else{
                logger.error("CryptocurrencyDetails with given name not found in DB",CryptocurrencyDetailsRepositoryCustomImpl.class);
            }
        }
        return c;
    }

    @Override
    public boolean cryptocurrencyDetailsExists(String name) {
        boolean exists=false;
         CryptocurrencyDetails cryptoDetails=null;
       cryptoDetails=findCryptocurrencyDetailsByName(name);
        if(cryptoDetails!=null){
            logger.info("CryptocurrencyDetails with name "+name+" found in db",CryptocurrencyDetailsRepositoryCustomImpl.class);
            exists=true;
        }else{
            logger.info("CryptocurrencyDetails with name "+name+" not found in db",CryptocurrencyDetailsRepositoryCustomImpl.class);
        }
        return exists;
    }
    
    
    
}
