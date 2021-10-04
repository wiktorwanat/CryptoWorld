/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.ServiceImpl;

import com.wwanat.CryptoWorld.Exception.EntityNotFoundException;
import com.wwanat.CryptoWorld.Model.CryptocurrencyDetails;
import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Repository.CryptocurrencyDetailsRepository;
import com.wwanat.CryptoWorld.Service.CryptocurrencyDetailsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;

/**
 * @author Wiktor
 */
@Service
public class CryptocurrencyDetailsServiceImpl implements CryptocurrencyDetailsService {

    private final static Logger logger = LoggerFactory.getLogger(CryptocurrencyDetailsServiceImpl.class);

    @Autowired
    private CryptocurrencyDetailsRepository cryptocurrencyDetailsRepository;

    @Override
    public CryptocurrencyDetails createCryptocurrencyDetails(CryptocurrencyDetails cryptocurrencyDetails) throws Exception {
        if (cryptocurrencyDetails != null) {
            CryptocurrencyDetails newCryptocurrencyDetails = cryptocurrencyDetailsRepository.save(cryptocurrencyDetails);
            cryptocurrencyDetails = cryptocurrencyDetailsRepository.save(cryptocurrencyDetails);
            logger.info("CryptocurrencyDetails created", cryptocurrencyDetails);
            return cryptocurrencyDetails;
        } else {
            logger.error("Exception thrown in createCryptocurrencyDetails method ", CryptocurrencyDetailsServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public CryptocurrencyDetails updateCryptocurrencyDetails(CryptocurrencyDetails cryptocurrencyDetails) throws Exception {
        if (cryptocurrencyDetails != null) {
            CryptocurrencyDetails cryptoDetails = null;
            cryptoDetails = findCryptocurrencyDetailsByName(cryptocurrencyDetails.getName());
            if (cryptoDetails == null) {
                logger.info("Creating " + cryptocurrencyDetails.getName() + " CryptocurrencyDetails ", CryptocurrencyDetailsServiceImpl.class);
                return createCryptocurrencyDetails(cryptocurrencyDetails);
            } else {
                cryptocurrencyDetails.setId(cryptoDetails.getId());
                logger.info("Updating " + cryptocurrencyDetails.getName() + " CryptocurrencyDetails ", CryptocurrencyDetailsServiceImpl.class);
                return cryptocurrencyDetailsRepository.save(cryptocurrencyDetails);
            }
        } else {
            logger.error("Exception thrown in updateCryptocurrencyDetails method ", CryptocurrencyDetailsServiceImpl.class);
            throw new IllegalArgumentException();
        }

    }

    @Override
    public void removeCryptocurrencyDetails(String id) throws Exception {
        if (id != null) {
            cryptocurrencyDetailsRepository.deleteById(id);
            logger.info("CryptocurrencyDetails removing success with ocject id", id);
        } else {
            logger.error("Exception thrown in removeCryptocurrencyDetails method ", CryptocurrencyDetailsServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<CryptocurrencyDetails> getAllCryptocurrencyDetails() {
        List<CryptocurrencyDetails> cryptocurrenciesDetails = new ArrayList<CryptocurrencyDetails>();
        cryptocurrenciesDetails = cryptocurrencyDetailsRepository.findAll();
        logger.info(cryptocurrenciesDetails.size() + " cryptocurrencies collected from database");
        return cryptocurrenciesDetails;
    }

    @Override
    public Optional<CryptocurrencyDetails> getById(String id) throws Exception {
        if (id != null) {
            Optional<CryptocurrencyDetails> cryptocurrencyDetails = cryptocurrencyDetailsRepository.findById(id);
            if (cryptocurrencyDetails != null) {
                logger.info("getById method with id " + id, CryptocurrencyDetailsServiceImpl.class);
                return cryptocurrencyDetails;
            } else {
                throw new EntityNotFoundException(CryptocurrencyDetails.class, "id", id);
            }
        } else {
            logger.info("Null pointer exception thrown in getById method", CryptocurrencyDetailsServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void removeCryptocurrencyDetailsByName(String name) throws Exception {
        if (name != null) {
            CryptocurrencyDetails cryptocurrencyDetails = null;
            cryptocurrencyDetails = findCryptocurrencyDetailsByName(name);
            if (cryptocurrencyDetails != null) {
                removeCryptocurrencyDetails(cryptocurrencyDetails.getId());
                logger.info("removeCryptocurrencyByName Method succesly remove object with name" + name, CryptocurrencyServiceImpl.class);
            } else {
                throw new EntityNotFoundException(CryptocurrencyDetails.class, "name", name);
            }
        } else {
            logger.info("Null pointer exception thrown in removeCryptocurrencyDetailsByName method", CryptocurrencyDetailsServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public CryptocurrencyDetails findCryptocurrencyDetailsByName(String name) {
        if (name != null) {
            return cryptocurrencyDetailsRepository.findCryptocurrencyDetailsByName(name);
        } else {
            logger.info("Null pointer exception thrown in findCryptocurrencyDetailsByName method", CryptocurrencyDetailsServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean CryptocurrencyDetailsExists(String name) {
        if (name != null) {
            return cryptocurrencyDetailsRepository.cryptocurrencyDetailsExists(name);
        } else {
            logger.info("Null pointer exception thrown in cryptocurrencyDetailsExists method", CryptocurrencyDetailsServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }


}
