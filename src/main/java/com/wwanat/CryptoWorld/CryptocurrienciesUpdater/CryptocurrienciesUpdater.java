/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.CryptocurrienciesUpdater;

import com.wwanat.CryptoWorld.CoinMarketCapController.CMCCryptocurrencyValueController;
import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Service.CryptocurrencyService;
import com.wwanat.CryptoWorld.Tools.JSONParser;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Wiktor
 */
@Component
public class CryptocurrienciesUpdater {
    
    @Autowired
    private CryptocurrencyService cryptocurrencyService;
    
    @Autowired
    private CMCCryptocurrencyValueController coinMarketCapController;
    
    
    
    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}", initialDelay = 1000)
    private void cryptocurrenciesDataUpdater(){
        //log4j info
        String CNCresponse="";
        try{
            CNCresponse=coinMarketCapController.makeAPICall();
            List<Cryptocurrency> cryptocurrencyList=JSONParser.fetchCryptocurrenciesFromJSONResponse(CNCresponse);
            for(Cryptocurrency crypto:cryptocurrencyList){
                cryptocurrencyService.updateCryptocurrency(crypto);
            }
        }catch(URISyntaxException e){
            //log4j info soon
            System.out.println(e.getMessage());
        }catch(IOException e2){
            System.out.println(e2.getMessage());
        }catch(Exception e3){
            System.out.println(e3.getMessage());
        }
        System.out.println(CNCresponse);
        
    }
    
}
