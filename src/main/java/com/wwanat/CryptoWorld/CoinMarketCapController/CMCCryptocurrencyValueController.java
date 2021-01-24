/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.CoinMarketCapController;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 *
 * @author Wiktor
 */
//CMC stand for CoinMarketCup
public interface CMCCryptocurrencyValueController {
    
    public String makeAPICallForCryptocurrencyValue()  throws URISyntaxException, IOException;
    public String makeAPICallForCryptocurrenyDetails(List<Cryptocurrency> cryptocurrency)  throws URISyntaxException, IOException;
}
