/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.ServiceImpl;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Repository.CryptocurrencyRepository;
import com.wwanat.CryptoWorld.Service.CryptocurrencyService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wiktor
 */
@Service
public class CryptocurrencyServiceImpl implements CryptocurrencyService{
    
    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Override
    public Cryptocurrency createCryptocurrency(Cryptocurrency cryptocurrency) throws Exception {
        cryptocurrency=cryptocurrencyRepository.save(cryptocurrency);
        return cryptocurrency;
    }

    @Override
    public Cryptocurrency updateCryptocurrency(Cryptocurrency cryptocurrency) throws Exception {
        cryptocurrency=cryptocurrencyRepository.save(cryptocurrency);
        return cryptocurrency;
    }

    @Override
    public void removeCryptocurrency(String id) throws Exception {
        if(id==null){
            throw new Exception("cryptocurrency id is nullable");
        }else{
            cryptocurrencyRepository.deleteById(id);
        }
    }

    @Override
    public List<Cryptocurrency> getAll() throws Exception {
        List<Cryptocurrency> cryptocurrencies=new ArrayList<Cryptocurrency>();
        cryptocurrencies=cryptocurrencyRepository.findAll();
        return cryptocurrencies;
    }

    @Override
    public Cryptocurrency getByName(String name) throws Exception {
        return null;
    }

    @Override
    public Optional<Cryptocurrency> getById(String id) throws Exception {
        Optional<Cryptocurrency> cryptocurrency=cryptocurrencyRepository.findById(id);
        return cryptocurrency;
    }
    
    
    

}
