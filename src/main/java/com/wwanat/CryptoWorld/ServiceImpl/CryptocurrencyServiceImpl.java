/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.ServiceImpl;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Repository.CryptocurrencyRepository;
import com.wwanat.CryptoWorld.RepositoryImpl.CryptocurrencyRepositoryCustomImpl;
import com.wwanat.CryptoWorld.Service.CryptocurrencyService;
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
public class CryptocurrencyServiceImpl implements CryptocurrencyService{
    
    final private static Logger logger = LoggerFactory.getLogger(CryptocurrencyServiceImpl.class);
    
    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Override
    public Cryptocurrency createCryptocurrency(Cryptocurrency cryptocurrency) throws Exception {
            cryptocurrency=cryptocurrencyRepository.save(cryptocurrency);
            logger.info("Creating new ryptocurrency", cryptocurrency);
            return cryptocurrency;
    }

    @Override
    public Cryptocurrency updateCryptocurrency(Cryptocurrency cryptocurrency) throws Exception {
        Cryptocurrency crypto=null;
        try{
            crypto=getByName(cryptocurrency.getName());
        }catch(Exception e){
             logger.error("Update failure", CryptocurrencyServiceImpl.class);
        }
        if(crypto==null){
            logger.info("Cryptocurrency created", CryptocurrencyServiceImpl.class);
            return cryptocurrencyRepository.save(cryptocurrency);
        }else{
            crypto.setName(cryptocurrency.getName());
            crypto.setSymbol(cryptocurrency.getSymbol());
            crypto.setActualPrice(cryptocurrency.getActualPrice());
            crypto.setMarketCap(cryptocurrency.getMarketCap());
            crypto.setPercent_change_1h(cryptocurrency.getPercent_change_1h());
            crypto.setPercent_change_24h(cryptocurrency.getPercent_change_24h());
            crypto.setPercent_change_7d(cryptocurrency.getPercent_change_7d());
            crypto.setSlug(cryptocurrency.getSlug());
            crypto.setVolume24h(cryptocurrency.getVolume24h());
            if(cryptocurrency.getCryptocurrencyDetails()!=null){
                crypto.setCryptocurrencyDetails(cryptocurrency.getCryptocurrencyDetails());
            }
            logger.info("Cryptocurrency "+crypto.getName()+" updated", CryptocurrencyServiceImpl.class);
            return cryptocurrencyRepository.save(crypto);
        }
    }

    @Override
    public void removeCryptocurrency(String id) throws Exception {
            if(id==null){
                logger.info("given Cryptocurreny id to remove is nullable",id);
                throw new Exception("cryptocurrency id is nullable");
            }else{
                cryptocurrencyRepository.deleteById(id);
                logger.info("Cryptocurreny removing succes with ocject id",id);
            }

    }

    @Override
    public List<Cryptocurrency> getAll(){
        List<Cryptocurrency> cryptocurrencies=new ArrayList<Cryptocurrency>();
        cryptocurrencies=cryptocurrencyRepository.findAll();
        logger.info(cryptocurrencies.size()+" cryptocurrencies collected from database");
        return cryptocurrencies;
    }

    @Override
    public Cryptocurrency getByName(String name) throws Exception {
        Cryptocurrency cryptocurrency=null;
        try{
            cryptocurrency=cryptocurrencyRepository.findCryptocurrencyByName(name);
        }catch(Exception e){
            logger.error(e.getMessage(), CryptocurrencyServiceImpl.class);
        }
        return cryptocurrency;
    }

    @Override
    public Optional<Cryptocurrency> getById(String id) throws Exception {
        Optional<Cryptocurrency> cryptocurrency=cryptocurrencyRepository.findById(id);
        return cryptocurrency;
    }

    @Override
    public void removeCryptocurrencyByName(String name) throws Exception {
        Cryptocurrency cryptocurrency=null;
        cryptocurrency=getByName(name);
        if(cryptocurrency!=null){
           removeCryptocurrency(cryptocurrency.getId());
           logger.info("removeCryptocurrencyByName Method succesly remove object with name"+name, CryptocurrencyServiceImpl.class);
        }
    }

}
