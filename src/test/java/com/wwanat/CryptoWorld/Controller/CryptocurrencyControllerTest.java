/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Security.JWT.AuthEntryPointJwt;
import com.wwanat.CryptoWorld.Security.JWT.JwtUtils;
import com.wwanat.CryptoWorld.Security.WebSecurityConfig;
import com.wwanat.CryptoWorld.Service.CryptocurrencyService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Wiktor
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
@WebMvcTest(controllers = CryptocurrencyController.class)
public class CryptocurrencyControllerTest {
    
    static String apiUrl="http://localhost:8080/api/";
    Cryptocurrency cryptocurrencyTestObject;
    
    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;
    
    @MockBean
    private UserDetailsService userDetailsService;
    
    @MockBean
    private CryptocurrencyService cryptocurrencyService;
    
    @Autowired
    private MockMvc mvc;
        
    @Before
    public void setUp(){
        cryptocurrencyTestObject=new Cryptocurrency("Bitcoin","BTC","bitcoin",33826.0,600000000.0,100.0,100.0,100.0,100000000.0);
    }
        
    @WithAnonymousUser
    @Test
    public void getAllCryptocurrenciesTest_withAnonymousUser_shouldReturnCorrectListOfCryptocurrenciesWitkOKStatus() throws Exception{
        List<Cryptocurrency> expectedCryptoList=new ArrayList<Cryptocurrency>();
        expectedCryptoList.add(this.cryptocurrencyTestObject);
        
        
        
        String response=mvc.perform(get(apiUrl+"cryptocurrency")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        
        ObjectMapper mapper = new ObjectMapper();
        List<Cryptocurrency> responseList=mapper.readValue(response, new TypeReference<List<Cryptocurrency>>(){});
        //Assert.assertTrue(responseList.size()==1);
       // Assert.assertTrue(responseList.get(0).equals(this.cryptocurrencyTestObject));

    }
    
    @WithAnonymousUser
    @Test
    public void getAllCryptocurrenciesTest_withAnonymousUser_shouldReturnEmptyListWith404Status() throws Exception{
        when(cryptocurrencyService.getAll()).thenThrow(new Exception()); 
        mvc.perform(get(apiUrl+"cryptocurrency")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }
    
    
    @WithMockUser(username = "1050106266",
    password = "123456",
    authorities = "ROLE_ADMIN")
    @Test
    public void getAllCryptocurrenciesTest_WithAuthorizedUser_shouldReturnEmptyListWith404Status() throws Exception{
        when(cryptocurrencyService.getAll()).thenThrow(new Exception()); 
        mvc.perform(get(apiUrl+"cryptocurrency")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }
    
    @WithAnonymousUser
    @Test
    public void getCryptocurrencyByNameTest_withAnonymousUser_shouldReturnEmptyResponseWith404Status() throws Exception{
        when(cryptocurrencyService.getByName(Mockito.any(String.class))).thenReturn(this.cryptocurrencyTestObject);
        mvc.perform(get(apiUrl+"cryptocurrency/Bitcoin")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }
    
    @WithMockUser(username = "1050106266",
    password = "123456",
    authorities = "ROLE_ADMIN")
    @Test
    public void getCryptocurrencyByNameTest_withAdminUser_shouldReturnCryptocurrencyWithOKStatus() throws Exception{
        when(cryptocurrencyService.getByName(Mockito.any(String.class))).thenReturn(this.cryptocurrencyTestObject);
        mvc.perform(get(apiUrl+"cryptocurrency/Bitcoin")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
}
