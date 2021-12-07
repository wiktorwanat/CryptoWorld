package com.wwanat.CryptoWorld.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.CryptocurrencyDetails;
import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Security.JWT.AuthEntryPointJwt;
import com.wwanat.CryptoWorld.Security.JWT.JwtUtils;
import com.wwanat.CryptoWorld.Service.CryptocurrencyDetailsService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CryptocurrencyDetailsController.class)
class CryptocurrencyDetailsControllerTest {

    static String apiUrl = "http://localhost:8080/api/";

    private Cryptocurrency cryptocurrencyTestObject;
    private CryptocurrencyDetails cryptocurrencyDetailsObject;
    private List<CryptocurrencyDetails> cryptocurrencyDetailsListObject;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private CryptocurrencyDetailsService cryptocurrencyDetailsService;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp() throws Exception {
        this.cryptocurrencyTestObject = new Cryptocurrency("Bitcoin", "BTC", "bitcoin", 33826.0, 600000000.0, 100.0, 100.0, 100.0, 100000000.0, null);
        this.cryptocurrencyDetailsObject = new CryptocurrencyDetails("Bitcoin", "opis", "logourl", "websiteurl", "twitterurl", "forumurl", "redditurl", "documentacjaurl", "sourcecode", "datastworzenia");
        this.cryptocurrencyDetailsObject.setId(null);
        this.cryptocurrencyDetailsListObject = new ArrayList<>();
        this.cryptocurrencyDetailsListObject.add(cryptocurrencyDetailsObject);
    }

    public CryptocurrencyDetailsControllerTest() {
    }


    @WithMockUser(username = "1050106266",
            password = "123456",
            authorities = "ROLE_ADMIN")
    @Test
    public void getAllCryptocurrenciesDetailsTest_WithAuthorizedUser_shouldReturnCryptocurrencyDetailsList() throws Exception {
        given(cryptocurrencyDetailsService.getAllCryptocurrencyDetails()).willReturn(this.cryptocurrencyDetailsListObject);
        String response = mvc.perform(get(apiUrl + "cryptocurrencyDetails/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<CryptocurrencyDetails> responseList = mapper.readValue(response, new TypeReference<List<CryptocurrencyDetails>>() {
        });
        Assert.assertTrue(responseList.size() == 1);
        Assert.assertTrue(responseList.get(0).getName().equals(this.cryptocurrencyDetailsObject.getName()));
    }

    @WithMockUser(username = "1050106266",
            password = "123456",
            authorities = "ROLE_USER")
    @Test
    public void getAllCryptocurrenciesDetailsTest_WithUNAuthorizedUser_shouldReturn403Status() throws Exception {
        given(cryptocurrencyDetailsService.getAllCryptocurrencyDetails()).willReturn(this.cryptocurrencyDetailsListObject);
        mvc.perform(get(apiUrl + "cryptocurrencyDetails/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    public void getAllCryptocurrenciesDetailsTest_WithoutAuthorization_shouldReturnEmptyResponseWith200Status() throws Exception {
        given(cryptocurrencyDetailsService.getAllCryptocurrencyDetails()).willReturn(this.cryptocurrencyDetailsListObject);
        String response = mvc.perform(get(apiUrl + "cryptocurrencyDetails/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(response.isEmpty());
    }

}
