/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Repository;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;

/**
 *
 * @author Wiktor
 */
public interface CryptocurrencyRepositoryCustom {
    
        Cryptocurrency findCryptocurrencyByName(String name);

        void deleteCryptocurrencyNotification(String cryptocurrencyId);

}
