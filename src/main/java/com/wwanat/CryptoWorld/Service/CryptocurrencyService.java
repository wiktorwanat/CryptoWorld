/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Service;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Wiktor
 */
public interface CryptocurrencyService {
    
    Cryptocurrency createCryptocurrency(Cryptocurrency cryptocurrency) throws Exception;

    Cryptocurrency updateCryptocurrency(Cryptocurrency cryptocurrency) throws Exception;

    void removeCryptocurrency(String id) throws Exception;

    List<Cryptocurrency> getAll() throws Exception;

    Cryptocurrency getByName(String name) throws Exception;

    Optional<Cryptocurrency> getById(String id) throws Exception;

    void removeCryptocurrencyByName(String name) throws Exception;

}
