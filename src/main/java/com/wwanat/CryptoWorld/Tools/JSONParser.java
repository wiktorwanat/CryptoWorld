/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Tools;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.CryptocurrencyDetails;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wiktor
 */
public class JSONParser {
    
    private final static Logger logger=LoggerFactory.getLogger(JSONParser.class);
    
    public static List<Cryptocurrency> fetchCryptocurrenciesFromJSONResponse(String response){
        List<Cryptocurrency> cryptocurrencyList=new ArrayList<Cryptocurrency>();
        if(response!=null && !response.isEmpty()){
            JSONObject obj = new JSONObject(response);
            obj.getJSONObject("status").getInt("error_code");
            if(obj.getJSONObject("status").getInt("error_code")==0){
                JSONArray arr=obj.getJSONArray("data");
                for(int i=0;i<arr.length();i++){
                    String name=arr.getJSONObject(i).getString("name");
                    String symbol=arr.getJSONObject(i).getString("symbol");
                    String slug=arr.getJSONObject(i).getString("slug");
                    JSONObject quote=arr.getJSONObject(i).getJSONObject("quote");
                    if(quote!=null){
                        JSONObject fiat=quote.getJSONObject("USD");
                        if(fiat!=null){
                            try{
                                double actualPrice=fiat.getDouble("price");
                                double volume24h=fiat.getDouble("volume_24h");
                                double percent_change_1h=fiat.getDouble("percent_change_1h");
                                double percent_change_24h=fiat.getDouble("percent_change_24h");
                                double percent_change_7d=fiat.getDouble("percent_change_7d");
                                double marketCap=fiat.getDouble("market_cap");
                                String lastUpdated=fiat.getString("last_updated");
                                Cryptocurrency c=new Cryptocurrency(name,symbol,slug,actualPrice,marketCap,percent_change_1h,percent_change_24h,percent_change_7d,volume24h,lastUpdated);
                                System.out.println(c.toString());
                                cryptocurrencyList.add(c);
                            }catch(NumberFormatException e){
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                }
            }else{
                System.out.println("Server error on CoinMarketCupSide");
            }
        }
        return cryptocurrencyList;
    }
    
    public static List<CryptocurrencyDetails> fetchCryptocurrenciesDetailsFromJSONResponse(String response){
        List<CryptocurrencyDetails> cryptocurrencyDetailsList=new ArrayList<CryptocurrencyDetails>();
        try{
            if(response!=null && !response.isEmpty()){
                JSONObject obj = new JSONObject(response);
                obj.getJSONObject("status").getInt("error_code");
                if(obj.getJSONObject("status").getInt("error_code")==0){
                    JSONObject data=obj.getJSONObject("data");
                    JSONObject urls=null;
                    Iterator<String> keys=data.keys();
                    while(keys.hasNext()){
                            String key = keys.next();
                        if (data.get(key) instanceof JSONObject) {
                            String name=data.getJSONObject(key).getString("name");
                            String description=data.getJSONObject(key).getString("description");
                            String logoUrl=data.getJSONObject(key).getString("logo");
                            String dateOfCreation=data.getJSONObject(key).getString("date_added");
                            urls=data.getJSONObject(key).getJSONObject("urls");
                            if(urls!=null){
                                    JSONArray website=urls.getJSONArray("website");
                                    String websiteUrl="";
                                    if(website.length()>0){
                                        websiteUrl=website.getString(0);
                                    }
                                    JSONArray twitter=urls.getJSONArray("twitter");
                                    String twitterUrl="";
                                    if(twitter.length()>0){
                                        twitterUrl=twitter.getString(0);
                                    }
                                    JSONArray message_board=urls.getJSONArray("message_board");
                                    String forumUrl="";
                                    if(message_board.length()>0){
                                        forumUrl=message_board.getString(0);
                                    }
                                    JSONArray reddit=urls.getJSONArray("reddit");
                                    String redditUrl="";
                                    if(reddit.length()>0){
                                        redditUrl=reddit.getString(0);
                                    }
                                    JSONArray technical_doc=urls.getJSONArray("technical_doc");
                                    String technicalDocumentationUrl="";
                                    if(technical_doc.length()>0){
                                        technicalDocumentationUrl=technical_doc.getString(0);
                                    }
                                    JSONArray source_code=urls.getJSONArray("source_code");
                                    String sourceCodeUrl="";
                                    if(source_code.length()>0){
                                        sourceCodeUrl=source_code.getString(0);
                                    }
                                    CryptocurrencyDetails cryptocurrencyDetails=new CryptocurrencyDetails(name,description,logoUrl,websiteUrl,twitterUrl,forumUrl,redditUrl,technicalDocumentationUrl,sourceCodeUrl,dateOfCreation);
                                    cryptocurrencyDetailsList.add(cryptocurrencyDetails);
                            }else{
                                logger.info("Urls are missing!");
                            }
                        }
                    }
                }else{
                    logger.error("Server error on CoinMarketCupSide");
                }
            }
        }catch(Exception e){
            logger.error("Server error on CoinMarketCupSide",e);
        }
        return cryptocurrencyDetailsList;
    }
    

}
