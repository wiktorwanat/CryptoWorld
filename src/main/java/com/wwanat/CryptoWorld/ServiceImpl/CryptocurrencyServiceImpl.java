/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.ServiceImpl;

import com.wwanat.CryptoWorld.Exception.EntityNotFoundException;
import com.wwanat.CryptoWorld.Model.*;
import com.wwanat.CryptoWorld.Model.Types.HistoricalValue;
import com.wwanat.CryptoWorld.Repository.CryptocurrencyDetailsRepository;
import com.wwanat.CryptoWorld.Repository.CryptocurrencyRepository;
import com.wwanat.CryptoWorld.Repository.NotificationRepository;
import com.wwanat.CryptoWorld.Repository.UserRepository;
import com.wwanat.CryptoWorld.RepositoryImpl.CryptocurrencyRepositoryCustomImpl;
import com.wwanat.CryptoWorld.Service.CryptocurrencyHistoricalValueService;
import com.wwanat.CryptoWorld.Service.CryptocurrencyService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wiktor
 */
@Service
public class CryptocurrencyServiceImpl implements CryptocurrencyService {

    final private static Logger logger = LoggerFactory.getLogger(CryptocurrencyServiceImpl.class);

    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Autowired
    private CryptocurrencyDetailsRepository cryptocurrencyDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CryptocurrencyHistoricalValueService cryptocurrencyHistoricalValueService;

    @Override
    public Cryptocurrency createCryptocurrency(Cryptocurrency cryptocurrency) throws Exception {
        if (cryptocurrency != null) {
            Cryptocurrency createdCryptocurrency = cryptocurrencyRepository.save(cryptocurrency);
            if (createdCryptocurrency != null) {
                logger.info("Creating new cryptocurrency", cryptocurrency);
                return createdCryptocurrency;
            } else {
                logger.error("Entity not found exception thrown in createCryptocurrency", CryptocurrencyServiceImpl.class);
                throw new EntityNotFoundException(Cryptocurrency.class, "cryptocurrency", cryptocurrency.toString());
            }
        } else {
            logger.info("Illegal argument in createCryptocurrency method", CryptocurrencyServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Cryptocurrency updateCryptocurrency(Cryptocurrency cryptocurrency) throws Exception {
        if (cryptocurrency != null) {
            Cryptocurrency exisitingCryptocurrency = getByName(cryptocurrency.getName());
            if (exisitingCryptocurrency == null) {
                logger.info("Given cryptocurrency doesn't exist - creating new object", CryptocurrencyServiceImpl.class);
                CryptocurrencyHistoricalValue newCryptocurrencyHistoricalValue = new CryptocurrencyHistoricalValue();
                newCryptocurrencyHistoricalValue.addHistoricalValueToList(new HistoricalValue(cryptocurrency));
                newCryptocurrencyHistoricalValue = cryptocurrencyHistoricalValueService.createCryptocurrencyHistoricalValue(newCryptocurrencyHistoricalValue);
                logger.info("Cryptocurrency Historical Value Object added to database", CryptocurrencyServiceImpl.class);
                cryptocurrency.setCryptocurrencyHistoricalValue(newCryptocurrencyHistoricalValue);
                logger.info("Cryptocurrency added to database", CryptocurrencyServiceImpl.class);
                return cryptocurrencyRepository.save(cryptocurrency);
            } else {
                logger.info("Given cryptocurrency exist - updating", CryptocurrencyServiceImpl.class);
                exisitingCryptocurrency.setName(cryptocurrency.getName());
                exisitingCryptocurrency.setSymbol(cryptocurrency.getSymbol());
                exisitingCryptocurrency.setActualPrice(cryptocurrency.getActualPrice());
                exisitingCryptocurrency.setMarketCap(cryptocurrency.getMarketCap());
                exisitingCryptocurrency.setPercent_change_1h(cryptocurrency.getPercent_change_1h());
                exisitingCryptocurrency.setPercent_change_24h(cryptocurrency.getPercent_change_24h());
                exisitingCryptocurrency.setPercent_change_7d(cryptocurrency.getPercent_change_7d());
                exisitingCryptocurrency.setSlug(cryptocurrency.getSlug());
                exisitingCryptocurrency.setVolume24h(cryptocurrency.getVolume24h());
                if (cryptocurrency.getCryptocurrencyDetails() != null) {
                    exisitingCryptocurrency.setCryptocurrencyDetails(cryptocurrency.getCryptocurrencyDetails());
                }
                CryptocurrencyHistoricalValue actualCryptocurrencyHistoricalValue = exisitingCryptocurrency.getCryptocurrencyHistoricalValue();
                actualCryptocurrencyHistoricalValue.addHistoricalValueToList(new HistoricalValue(cryptocurrency));
                cryptocurrencyHistoricalValueService.updateCryptocurrencyHistoricalValue(actualCryptocurrencyHistoricalValue);
                logger.info("Cryptocurrency " + exisitingCryptocurrency.getName() + " updated", CryptocurrencyServiceImpl.class);
                return cryptocurrencyRepository.save(exisitingCryptocurrency);
            }
        } else {
            logger.info("Illegal argument in updateCryptocurrency method", CryptocurrencyServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Cryptocurrency updateDetailsForCryptocurrency(Cryptocurrency cryptocurrency) throws Exception {
        if (cryptocurrency != null) {
                logger.info("Cryptocurrency " + cryptocurrency.getName() + " updated", CryptocurrencyServiceImpl.class);
                return cryptocurrencyRepository.save(cryptocurrency);
        } else {
            logger.info("Illegal argument in updateCryptocurrency method", CryptocurrencyServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void removeCryptocurrency(String id) throws Exception {
        if (id != null) {
            Cryptocurrency cryptocurrency = cryptocurrencyRepository.findById(id).get();
            if(cryptocurrency!=null) {
                if (cryptocurrency.getCryptocurrencyDetails() != null) {
                    cryptocurrencyDetailsRepository.deleteById(cryptocurrency.getCryptocurrencyDetails().getId());
                }
                if (cryptocurrency.getCryptocurrencyHistoricalValue() != null) {
                    cryptocurrencyHistoricalValueService.removeCryptocurrencyHistoricalValue(cryptocurrency.getCryptocurrencyHistoricalValue());
                }
                List<User> users = userRepository.findAll();
                if (!users.isEmpty()) {
                    for (User u : users) {
                        if (!u.getUserCryptocurrency().isEmpty()) {
                            u.getUserCryptocurrency().removeIf(a -> a.getId().equals(id));
                            userRepository.save(u);
                        } else {
                            logger.error("Entity not found exception thrown in removeCryptocurrency", CryptocurrencyServiceImpl.class);
                            throw new EntityNotFoundException(Cryptocurrency.class, "id", id);
                        }
                    }
                } else {
                    logger.error("Entity not found exception thrown in removeCryptocurrency", CryptocurrencyServiceImpl.class);
                    throw new EntityNotFoundException(Cryptocurrency.class, "id", id);
                }
                cryptocurrencyRepository.deleteById(id);
                logger.info("Cryptocurrency successfully removed with id", id);
            } else {
                logger.info("Illegal argument in removeCryptocurrency method - cannot find object with given id to remove", CryptocurrencyServiceImpl.class);
                throw new IllegalArgumentException();
            }
        } else {
            logger.info("Illegal argument in removeCryptocurrency method", CryptocurrencyServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }


    @Override
    public List<Cryptocurrency> getAll() {
        List<Cryptocurrency> cryptocurrencies = new ArrayList<Cryptocurrency>();
        cryptocurrencies = cryptocurrencyRepository.findAll();
        logger.info(cryptocurrencies.size() + " cryptocurrencies collected from database");
        return cryptocurrencies;
    }

    @Override
    public Cryptocurrency getByName(String name) throws Exception {
        Cryptocurrency cryptocurrency = null;
        if (name != null) {
            cryptocurrency = cryptocurrencyRepository.findCryptocurrencyByName(name);
            if (cryptocurrency != null) {
                logger.info("Cryptocurrency collected from database");
            }
        } else {
            logger.info("Illegal argument in getByName method", CryptocurrencyServiceImpl.class);
            throw new IllegalArgumentException();
        }
        return cryptocurrency;
    }

    @Override
    public Optional<Cryptocurrency> getById(String id) throws Exception {
        if (id != null) {
            Optional<Cryptocurrency> cryptocurrency = cryptocurrencyRepository.findById(id);
            if (cryptocurrency != null) {
                logger.info("Cryptocurrency collected from database");
                return cryptocurrency;
            } else {
                logger.error("Entity not found exception thrown in getById", CryptocurrencyServiceImpl.class);
                throw new EntityNotFoundException(Cryptocurrency.class, "id", id);
            }
        } else {
            logger.info("Illegal argument in getByName method", CryptocurrencyServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void removeCryptocurrencyByName(String name) throws Exception {
        Cryptocurrency cryptocurrency = null;
        if (name != null) {
            cryptocurrency = getByName(name);
            if (cryptocurrency != null) {
                cryptocurrencyRepository.deleteById(cryptocurrency.getId());
                logger.info("Removing cryptocurrency object with name" + name, CryptocurrencyServiceImpl.class);
            } else {
                logger.error("Entity not found exception thrown in removeCryptocurrencyByName", CryptocurrencyServiceImpl.class);
                throw new EntityNotFoundException(Cryptocurrency.class, "name", name);
            }
        } else {
            logger.info("Illegal argument in getByName method", CryptocurrencyServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

}
