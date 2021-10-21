/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.Types.UserRole;
import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Security.JWT.AuthEntryPointJwt;
import com.wwanat.CryptoWorld.Security.JWT.JwtUtils;
import com.wwanat.CryptoWorld.Service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willAnswer;

import org.mockito.Mockito;

import static org.mockito.Mockito.doNothing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Wiktor
 */
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

    static String apiUrl = "http://localhost:8080/api/";

    private Cryptocurrency cryptocurrencyTestObject;
    private User testUserObject;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        this.cryptocurrencyTestObject = new Cryptocurrency("Bitcoin", "BTC", "bitcoin", 33826.0, 600000000.0, 100.0, 100.0, 100.0, 100000000.0,null);
        this.testUserObject = new User("user", "user", "user@gmail.com");
        List roleList = new ArrayList();
        roleList.add(UserRole.ROLE_USER);
        this.testUserObject.setRoles(roleList);
        this.testUserObject.addFavouriteCryptocurrency(this.cryptocurrencyTestObject);
    }

    @WithMockUser(username = "user", password = "user", authorities = "ROLE_USER")
    @Test
    public void getUserFavouriteCryptocurrenciesListTest_WithAuthorizedUser_shouldCorrectlyReturnFavouriteCryptocurrenciesListWithOkStatus() throws Exception {
        given(this.userService.getUserFavouriteCryptocurrencies(Mockito.any(String.class))).willReturn(this.testUserObject.getUserCryptocurrency());

        String response = mvc.perform(get(this.apiUrl + "myCryptocurrencies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        List<Cryptocurrency> returnedCryptocurrencyList = mapper.readValue(response, new TypeReference<List<Cryptocurrency>>() {
        });
        Assert.assertNotNull(returnedCryptocurrencyList);
        Assert.assertTrue(returnedCryptocurrencyList.get(0).equals(this.testUserObject.getUserCryptocurrency().get(0)));
    }

    @WithMockUser(username = "user", password = "user", authorities = "ROLE_ADMIN")
    @Test
    public void getUserFavouriteCryptocurrenciesListTest_WithUnAuthorizedUser_shouldReturnEmptyResponseWith403Status() throws Exception {
        given(this.userService.getUserFavouriteCryptocurrencies(Mockito.any(String.class))).willReturn(this.testUserObject.getUserCryptocurrency());

        String response = mvc.perform(get(this.apiUrl + "myCryptocurrencies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403))
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(response.isEmpty());
    }

    @WithMockUser(username = "user", password = "user", authorities = "ROLE_USER")
    @Test
    public void getUserFavouriteCryptocurrenciesListTest__withServerError_shouldReturnEmptyResponseWith404Status() throws Exception {
        given(this.userService.getUserFavouriteCryptocurrencies(Mockito.any(String.class))).willAnswer(invocation -> {
            throw new Exception();
        });

        mvc.perform(get(this.apiUrl + "myCryptocurrencies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @WithMockUser(username = "user", password = "user", authorities = "ROLE_USER")
    @Test
    public void addCryptocurrencyToUserFavouriteListTest_WithAuthorizedUser_shouldEndWithOkStatus() throws Exception {
        doNothing().when(this.userService).addCryptocurrencyToFavourite(Mockito.any(String.class), Mockito.any(String.class));

        mvc.perform(MockMvcRequestBuilders.post(this.apiUrl + "cryptocurrency/Bitcoin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", password = "user", authorities = "ROLE_ADMIN")
    @Test
    public void addCryptocurrencyToUserFavouriteListTest_WithUnAuthorizedUser_shouldReturnEmptyResponseWith403Status() throws Exception {
        doNothing().when(this.userService).addCryptocurrencyToFavourite(Mockito.any(String.class), Mockito.any(String.class));
        String response = mvc.perform(MockMvcRequestBuilders.post(this.apiUrl + "cryptocurrency/Bitcoin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403))
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(response.isEmpty());
    }

    @WithMockUser(username = "user", password = "user", authorities = "ROLE_USER")
    @Test
    public void addCryptocurrencyToUserFavouriteListTest__withServerError_shouldReturnEmptyResponseWith404Status() throws Exception {
        willAnswer(invocation -> {
            throw new Exception();
        }).willDoNothing().given(this.userService).addCryptocurrencyToFavourite(Mockito.any(String.class), Mockito.any(String.class));

        mvc.perform(MockMvcRequestBuilders.post(this.apiUrl + "cryptocurrency/Bitcoin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @WithMockUser(username = "user", password = "user", authorities = "ROLE_USER")
    @Test
    public void removeCryptocurrencyFromUserFavouriteListTest_WithAuthorizedUser_shouldEndWithOkStatus() throws Exception {
        doNothing().when(this.userService).removeCryptocurrencyFromFavourite(Mockito.any(String.class), Mockito.any(String.class));

        mvc.perform(MockMvcRequestBuilders.delete(this.apiUrl + "myCryptocurrencies/remove/Bitcoin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", password = "user", authorities = "ROLE_ADMIN")
    @Test
    public void removeCryptocurrencyFromUserFavouriteListTest_WithUnAuthorizedUser_shouldReturnEmptyResponseWith403Status() throws Exception {
        doNothing().when(this.userService).removeCryptocurrencyFromFavourite(Mockito.any(String.class), Mockito.any(String.class));
        String response = mvc.perform(MockMvcRequestBuilders.delete(this.apiUrl + "myCryptocurrencies/remove/Bitcoin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403))
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(response.isEmpty());
    }

    @WithMockUser(username = "user", password = "user", authorities = "ROLE_USER")
    @Test
    public void removeCryptocurrencyFromUserFavouriteListTest__withServerError_shouldReturnEmptyResponseWith404Status() throws Exception {
        willAnswer(invocation -> {
            throw new Exception();
        }).willDoNothing().given(this.userService).removeCryptocurrencyFromFavourite(Mockito.any(String.class), Mockito.any(String.class));

        mvc.perform(MockMvcRequestBuilders.delete(this.apiUrl + "myCryptocurrencies/remove/Bitcoin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }


}
