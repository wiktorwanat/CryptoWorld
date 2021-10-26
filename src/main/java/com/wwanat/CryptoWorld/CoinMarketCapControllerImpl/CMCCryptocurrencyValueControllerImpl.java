/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.CoinMarketCapControllerImpl;

import com.wwanat.CryptoWorld.CoinMarketCapController.CMCCryptocurrencyValueController;
import com.wwanat.CryptoWorld.Model.Cryptocurrency;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Wiktor
 */
//CMC stand for CoinMarketCup
@Service
public class CMCCryptocurrencyValueControllerImpl implements CMCCryptocurrencyValueController {

    final static Logger logger = LoggerFactory.getLogger(CMCCryptocurrencyValueControllerImpl.class);

    @Value("${cmc.api.key}")
    private String apiKey;

    @Value("${cmc.api.cryptoValuesUrl}")
    private String cryptoValuesUrl;

    @Value("${cmc.api.cryptoDetailsUrl}")
    private String cryptoDetailsUrl;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getCryptoValuesUrl() {
        return cryptoValuesUrl;
    }

    public void setCryptoValuesUrl(String cryptoValuesUrl) {
        this.cryptoValuesUrl = cryptoValuesUrl;
    }

    public String getCryptoDetailsUrl() {
        return cryptoDetailsUrl;
    }

    public void setCryptoDetailsUrl(String cryptoDetailsUrl) {
        this.cryptoDetailsUrl = cryptoDetailsUrl;
    }


    public String makeAPICallForCryptocurrencyValue() throws URISyntaxException, IOException {
        String response_content = "";

        logger.info("calling " + getCryptoValuesUrl(), CMCCryptocurrencyValueControllerImpl.class);

        URIBuilder query = new URIBuilder(getCryptoValuesUrl());

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", getApiKey());

        CloseableHttpResponse response = client.execute(request);

        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            System.out.println(entity.getContentType());
            EntityUtils.consume(entity);
            logger.info("call ended successfully ,data fetched", CMCCryptocurrencyValueControllerImpl.class);
        } finally {
            response.close();
        }
        return response_content;
    }


    @Override
    public String makeAPICallForCryptocurrenyDetails(List<Cryptocurrency> cryptocurrency) throws URISyntaxException, IOException {
        String response_content = "";
        String fullUrlBuild = "";

        logger.info("calling " + fullUrlBuild, CMCCryptocurrencyValueControllerImpl.class);

        fullUrlBuild = buildCryptocurrencyDetailsUrl(cryptocurrency);

        URIBuilder query = new URIBuilder(fullUrlBuild);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", getApiKey());

        CloseableHttpResponse response = client.execute(request);

        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            System.out.println(entity.getContentType());
            EntityUtils.consume(entity);
            logger.info("Call ended successfully ,data fetched", CMCCryptocurrencyValueControllerImpl.class);
        } finally {
            response.close();
        }
        return response_content;
    }


    public String buildCryptocurrencyDetailsUrl(List<Cryptocurrency> cryptocurrencies) {
        String urlOut = getCryptoDetailsUrl();
        String slugUrl = "?slug=";
        if (cryptocurrencies != null) {
            urlOut += slugUrl;
            for (Cryptocurrency c : cryptocurrencies) {
                urlOut += c.getSlug() + ",";
            }
            urlOut = urlOut.substring(0, urlOut.length() - 1);
        }
        return urlOut;
    }
}
