/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.CoinMarketCapController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.NameValuePair;

/**
 *
 * @author Wiktor
 */
//CMC stand for CoinMarketCup
public interface CMCCryptocurrencyValueController {
    
    public String makeAPICall(List<NameValuePair> parameters)  throws URISyntaxException, IOException;
    
}
