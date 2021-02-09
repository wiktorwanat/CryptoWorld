/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Controller;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Service.CryptocurrencyService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 *
 * @author Wiktor
 */
@ExtendWith(MockitoExtension.class)
public class CryptocurrencyControllerTest {
    
    final static Logger logger =LoggerFactory.getLogger(CryptocurrencyControllerTest.class);
    
    
    @MockBean
    private CryptocurrencyController cryptocurrencyController;
    
    @Mock
    private CryptocurrencyService cryptocurrencyService;
    
    
    @Test
    public void getAllCryptocurrenciesTest_shouldReturnCorrectListOfCryptocurrencies(){
        List<Cryptocurrency> expectedCryptoList=new ArrayList<>();
        expectedCryptoList.add(new Cryptocurrency("Bitcoin","BTC","bitcoin",33826.0,600000000.0,100.0,100.0,100.0,100000000.0));
        try{
            when(cryptocurrencyService.getAll()).thenReturn(expectedCryptoList);
        }catch(Exception e){ 
            logger.warn(e.getMessage());
        }
    }
    
}
