/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.ServiceImpl;

import com.wwanat.CryptoWorld.Model.CryptocurrencyDetails;
import com.wwanat.CryptoWorld.Repository.CryptocurrencyDetailsRepository;
import com.wwanat.CryptoWorld.Service.CryptocurrencyDetailsService;
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
public class CryptocurrencyDetailsServiceImpl implements CryptocurrencyDetailsService{
    
    private final static Logger logger=LoggerFactory.getLogger(CryptocurrencyDetailsServiceImpl.class);
    
    @Autowired 
    private CryptocurrencyDetailsRepository cryptocurrencyDetailsRepository;

    @Override
    public CryptocurrencyDetails createCryptocurrencyDetails(CryptocurrencyDetails cryptocurrencyDetails) throws Exception {
            cryptocurrencyDetails=cryptocurrencyDetailsRepository.save(cryptocurrencyDetails);
            logger.info("CryptocurrencyDetails created", cryptocurrencyDetails);    
            return cryptocurrencyDetails;
    }

    @Override
    public CryptocurrencyDetails updateCryptocurrencyDetails(CryptocurrencyDetails cryptocurrencyDetails) throws Exception {
            CryptocurrencyDetails cryptoDetails=null;
        try{
            cryptoDetails=findCryptocurrencyDetailsByName(cryptocurrencyDetails.getName());
        }catch(Exception e){
             logger.error("Update failure", CryptocurrencyDetailsServiceImpl.class);
        }
        if(cryptoDetails==null){
            logger.info("Creating "+cryptocurrencyDetails.getName()+" CryptocurrencyDetails ", CryptocurrencyDetailsServiceImpl.class);
            return createCryptocurrencyDetails(cryptocurrencyDetails);
        }else{
            cryptocurrencyDetails.setId(cryptoDetails.getId());
            logger.info("Updating "+cryptocurrencyDetails.getName()+" CryptocurrencyDetails ", CryptocurrencyDetailsServiceImpl.class);
            return cryptocurrencyDetailsRepository.save(cryptocurrencyDetails);
        }
    }

    @Override
    public void removeCryptocurrencyDetails(String id) throws Exception {
        if(id==null){
                logger.info("given CryptocurrencyDetails id to remove is nullable",id);
                throw new Exception("CryptocurrencyDetails id is nullable");
            }else{
                cryptocurrencyDetailsRepository.deleteById(id);
                logger.info("CryptocurrencyDetails removing succes with ocject id",id);
            }
    }

    @Override
    public List<CryptocurrencyDetails> getAllCryptocurrencyDetails() throws Exception {
         List<CryptocurrencyDetails> cryptocurrenciesDetails=new ArrayList<CryptocurrencyDetails>();
        cryptocurrenciesDetails=cryptocurrencyDetailsRepository.findAll();
        logger.info(cryptocurrenciesDetails.size()+" cryptocurrencies collected from database");
        return cryptocurrenciesDetails;
    }

    @Override
    public Optional<CryptocurrencyDetails> getById(String id) throws Exception {
        Optional<CryptocurrencyDetails> cryptocurrencyDetails=cryptocurrencyDetailsRepository.findById(id);
        return cryptocurrencyDetails;
    }

    @Override
    public void removeCryptocurrencyDetailsByName(String name) throws Exception {
        CryptocurrencyDetails cryptocurrencyDetails=null;
        cryptocurrencyDetails=findCryptocurrencyDetailsByName(name);
        if(cryptocurrencyDetails!=null){
           removeCryptocurrencyDetails(cryptocurrencyDetails.getId());
           logger.info("removeCryptocurrencyByName Method succesly remove object with name"+name, CryptocurrencyServiceImpl.class);
        }
    }

    @Override
    public CryptocurrencyDetails findCryptocurrencyDetailsByName(String name) {
        return cryptocurrencyDetailsRepository.findCryptocurrencyDetailsByName(name);
    }

    @Override
    public boolean CryptocurrencyDetailsExists(String name) {
        return cryptocurrencyDetailsRepository.CryptocurrencyDetailsExists(name);
    }


    
    
}
