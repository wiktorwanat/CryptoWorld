/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.CryptocurrienciesUpdater;

import com.wwanat.CryptoWorld.CoinMarketCapController.CMCCryptocurrencyValueController;
import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.CryptocurrencyDetails;
import com.wwanat.CryptoWorld.Service.CryptocurrencyDetailsService;
import com.wwanat.CryptoWorld.Service.CryptocurrencyService;
import com.wwanat.CryptoWorld.Tools.JSONParser;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Wiktor
 */
@Component
public class CryptocurrienciesUpdater {
    
    final private static Logger logger = LoggerFactory.getLogger(CryptocurrienciesUpdater.class);
    
    @Autowired
    private CryptocurrencyService cryptocurrencyService;
    
    @Autowired
    private CryptocurrencyDetailsService cryptocurrencyDetailsService;
    
    
    @Autowired
    private CMCCryptocurrencyValueController coinMarketCapController;
    
    
    
    @Scheduled(fixedDelayString = "${cryptocurrenciesDataUpdater.fixedDelay.in.milliseconds}", initialDelay = 30000)
    private void cryptocurrenciesDataUpdater(){
        
        String CNCresponse="";
        try{
            CNCresponse=coinMarketCapController.makeAPICallForCryptocurrencyValue();
            List<Cryptocurrency> cryptocurrencyList=JSONParser.fetchCryptocurrenciesFromJSONResponse(CNCresponse);
            for(Cryptocurrency crypto:cryptocurrencyList){
                cryptocurrencyService.updateCryptocurrency(crypto);
            }
        }catch(URISyntaxException e){
            logger.info("cryptoUpdater throws Exception", CryptocurrienciesUpdater.class);
            logger.error(e.getMessage(), CryptocurrienciesUpdater.class);
        }catch(IOException e2){
            logger.info("cryptoUpdater throws Exception", CryptocurrienciesUpdater.class);
            logger.error(e2.getMessage(), CryptocurrienciesUpdater.class);
        }catch(Exception e3){
            logger.info("cryptoUpdater throws Exception", CryptocurrienciesUpdater.class);
            logger.error(e3.getMessage(), CryptocurrienciesUpdater.class);
        }
        logger.info(CNCresponse,CryptocurrienciesUpdater.class);
    }
    
    
    @Scheduled(fixedDelayString="${cryptocurrenciesDataUpdater.fixedDelay.in.milliseconds}",initialDelay=60000)
    public void cryptocurrencyDetailsUpdater(){
        String CNCresponse="";
        List<Cryptocurrency> cryptocurrencies=new ArrayList<Cryptocurrency>();
        try{
            cryptocurrencies=cryptocurrencyService.getAll();
            CNCresponse=coinMarketCapController.makeAPICallForCryptocurrenyDetails(cryptocurrencies);
            List<CryptocurrencyDetails> cryptocurrenciesDetails=JSONParser.fetchCryptocurrenciesDetailsFromJSONResponse(CNCresponse);
            Cryptocurrency matchingCryptocurrency=null;
            for(CryptocurrencyDetails cryptocurrencyDetail:cryptocurrenciesDetails){
                cryptocurrencyDetailsService.updateCryptocurrencyDetails(cryptocurrencyDetail);
                matchingCryptocurrency=cryptocurrencyService.getByName(cryptocurrencyDetail.getName());
                if(matchingCryptocurrency!=null){
                        matchingCryptocurrency.setCryptocurrencyDetails(cryptocurrencyDetail);
                        cryptocurrencyService.updateCryptocurrency(matchingCryptocurrency);
                        logger.info("Cryptocurrency "+matchingCryptocurrency.getName()+" matched with details and updated ", CryptocurrienciesUpdater.class);
                }else{
                    logger.warn("CryptocurrencyDetails for "+cryptocurrencyDetail.getName()+"not found any matching cryptocurrency do connect", CryptocurrienciesUpdater.class);
                }
            }
        }catch(URISyntaxException e){
            logger.info("cryptoUpdater throws Exception", CryptocurrienciesUpdater.class);
            logger.error(e.getMessage(), CryptocurrienciesUpdater.class);
        }catch(IOException e2){
            logger.info("cryptoUpdater throws Exception", CryptocurrienciesUpdater.class);
            logger.error(e2.getMessage(), CryptocurrienciesUpdater.class);
        }catch(Exception e3){
            logger.info("cryptoUpdater throws Exception", CryptocurrienciesUpdater.class);
            logger.error(e3.getMessage(), CryptocurrienciesUpdater.class);
        }
        logger.info(CNCresponse,CryptocurrienciesUpdater.class);
    }

}
