/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.CoinMarketCapController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Wiktor
 */
//CMC stand for CoinMarketCup
public class CMCCryptocurrencyValueControllerImpl implements CMCCryptocurrencyValueController{
    
    @Value("${cmc.api.key}")
    private String apiKey;

    @Value("${cmc.api.url}")
    private String uri;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


  public String makeAPICall(List<NameValuePair> parameters) throws URISyntaxException, IOException {
    String response_content = "";

    URIBuilder query = new URIBuilder(getUri());
    query.addParameters(parameters);

    CloseableHttpClient client = HttpClients.createDefault();
    HttpGet request = new HttpGet(query.build());

    request.setHeader(HttpHeaders.ACCEPT, "application/json");
    request.addHeader("X-CMC_PRO_API_KEY", getApiKey());

    CloseableHttpResponse response = client.execute(request);

    try {
      System.out.println(response.getStatusLine());
      HttpEntity entity = response.getEntity();
      response_content = EntityUtils.toString(entity);
      EntityUtils.consume(entity);
    } finally {
      response.close();
    }

    return response_content;
  }
}


   // List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
    //paratmers.add(new BasicNameValuePair("start","1"));
    //paratmers.add(new BasicNameValuePair("limit","5000"));
    //paratmers.add(new BasicNameValuePair("convert","USD"));

    //try {
    //  String result = makeAPICall(uri, paratmers);
    //  System.out.println(result);
    //} catch (IOException e) {
    //  System.out.println("Error: cannont access content - " + e.toString());
    //} catch (URISyntaxException e) {
     // System.out.println("Error: Invalid URL " + e.toString());
    //}