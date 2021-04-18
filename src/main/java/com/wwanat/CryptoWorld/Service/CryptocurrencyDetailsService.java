/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Service;

import com.wwanat.CryptoWorld.Model.CryptocurrencyDetails;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Wiktor
 */
public interface CryptocurrencyDetailsService{
    
    CryptocurrencyDetails createCryptocurrencyDetails(CryptocurrencyDetails cryptocurrencyDetails) throws Exception;

    CryptocurrencyDetails updateCryptocurrencyDetails(CryptocurrencyDetails cryptocurrencyDetails) throws Exception;

    void removeCryptocurrencyDetails(String id) throws Exception;

    List<CryptocurrencyDetails> getAllCryptocurrencyDetails();

    Optional<CryptocurrencyDetails> getById(String id) throws Exception;

    void removeCryptocurrencyDetailsByName(String name) throws Exception;

    CryptocurrencyDetails findCryptocurrencyDetailsByName(String name);

     boolean CryptocurrencyDetailsExists(String name);
}
