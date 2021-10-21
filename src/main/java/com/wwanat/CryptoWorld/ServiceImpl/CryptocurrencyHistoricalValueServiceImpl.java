package com.wwanat.CryptoWorld.ServiceImpl;

import com.wwanat.CryptoWorld.Exception.EntityNotFoundException;
import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.CryptocurrencyHistoricalValue;
import com.wwanat.CryptoWorld.Repository.CryptocurrencyHistoricalValueRepository;
import com.wwanat.CryptoWorld.Service.CryptocurrencyHistoricalValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CryptocurrencyHistoricalValueServiceImpl implements CryptocurrencyHistoricalValueService {

    final private static Logger logger = LoggerFactory.getLogger(CryptocurrencyHistoricalValueServiceImpl.class);

    @Autowired
    private CryptocurrencyHistoricalValueRepository cryptocurrencyHistoricalValueRepository;

    @Override
    public CryptocurrencyHistoricalValue createCryptocurrencyHistoricalValue(CryptocurrencyHistoricalValue cryptocurrencyHistoricalValue) throws Exception{
        CryptocurrencyHistoricalValue newCryptocurrencyHistoricalValue=null;
        if(cryptocurrencyHistoricalValue!=null){
            newCryptocurrencyHistoricalValue = cryptocurrencyHistoricalValueRepository.save(cryptocurrencyHistoricalValue);
            if(newCryptocurrencyHistoricalValue!=null){
                logger.info("Cryptocurrency Historical Value object has been created",CryptocurrencyHistoricalValueServiceImpl.class);
            }
        } else {
            logger.info("Illegal argument in createCryptocurrencyHistoricalValue method", CryptocurrencyHistoricalValueServiceImpl.class);
            throw new IllegalArgumentException();
        }
        return newCryptocurrencyHistoricalValue;
    }

    @Override
    public void removeCryptocurrencyHistoricalValue(CryptocurrencyHistoricalValue cryptocurrencyHistoricalValue)  throws Exception{
        if(cryptocurrencyHistoricalValue!=null){
            cryptocurrencyHistoricalValueRepository.delete(cryptocurrencyHistoricalValue);
            logger.info("Cryptocurrency Historical Value object deleted from database", CryptocurrencyHistoricalValueServiceImpl.class);
        } else {
            logger.info("Illegal argument in removeCryptocurrencyHistoricalValue method", CryptocurrencyHistoricalValueServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void removeCryptocurrencyHistoricalValueById(String cryptocurrencyHistoricalValueId) throws Exception {
        if(cryptocurrencyHistoricalValueId!=null){
            cryptocurrencyHistoricalValueRepository.deleteById(cryptocurrencyHistoricalValueId);
            logger.info("Cryptocurrency Historical Value object deleted from database", CryptocurrencyHistoricalValueServiceImpl.class);
        } else {
            logger.info("Illegal argument in removeCryptocurrencyHistoricalValueById method", CryptocurrencyHistoricalValueServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public CryptocurrencyHistoricalValue updateCryptocurrencyHistoricalValue(CryptocurrencyHistoricalValue cryptocurrencyHistoricalValue)  throws Exception{
        CryptocurrencyHistoricalValue updatedCryptocurrencyHistoricalValue = null;
        if(cryptocurrencyHistoricalValue!=null){
            if(cryptocurrencyHistoricalValue.getId()==null){
                logger.info("Cryptocurrency Historical Value object to update have nullable id throwing Exception", CryptocurrencyHistoricalValueServiceImpl.class);
                throw new IllegalArgumentException();
            }else {
                logger.info("Cryptocurrency Historical Value object updated", CryptocurrencyHistoricalValueServiceImpl.class);
                updatedCryptocurrencyHistoricalValue = cryptocurrencyHistoricalValueRepository.save(cryptocurrencyHistoricalValue);
            }
        }else{
            logger.info("Cryptocurrency Historical Value object to update is nullable - throwing Exception", CryptocurrencyHistoricalValueServiceImpl.class);
            throw new IllegalArgumentException();
        }
        return updatedCryptocurrencyHistoricalValue;
    }

    @Override
    public Optional<CryptocurrencyHistoricalValue> getCryptocurrencyHistoricalValueById(String cryptocurrencyHistoricalValueId)  throws Exception{
        Optional<CryptocurrencyHistoricalValue> cryptocurrencyHistoricalValue = null;
        if(cryptocurrencyHistoricalValueId!=null){
            cryptocurrencyHistoricalValue = cryptocurrencyHistoricalValueRepository.findById(cryptocurrencyHistoricalValueId);
            if(cryptocurrencyHistoricalValue!=null){
                logger.info("Cryptocurrency Historical Value object collected from database",CryptocurrencyHistoricalValueServiceImpl.class);
                return cryptocurrencyHistoricalValue;
            } else {
                logger.error("Entity not found exception thrown in getCryptocurrencyHistoricalValueById", CryptocurrencyHistoricalValueServiceImpl.class);
                throw new EntityNotFoundException(Cryptocurrency.class, "id", cryptocurrencyHistoricalValueId);
            }
        }else{
            logger.info("Illegal argument in getCryptocurrencyHistoricalValueById method", CryptocurrencyHistoricalValueServiceImpl.class);
            throw new IllegalArgumentException();
        }
    }
}
