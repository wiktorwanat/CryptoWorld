/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Repository;

import com.wwanat.CryptoWorld.Model.CryptocurrencyDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wiktor
 */
@Repository
public interface CryptocurrencyDetailsRepository extends MongoRepository<CryptocurrencyDetails, String>, CryptocurrencyDetailsRepositoryCustom {

    CryptocurrencyDetails findCryptocurrencyDetailsByName(String name);

    boolean cryptocurrencyDetailsExists(String name);

}
