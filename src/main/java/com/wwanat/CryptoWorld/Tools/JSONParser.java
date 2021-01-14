/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Tools;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

/**
 *
 * @author Wiktor
 */
public class JSONParser {
    
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
                                Cryptocurrency c=new Cryptocurrency(name,symbol,actualPrice,marketCap,percent_change_1h,percent_change_24h,percent_change_7d,volume24h);
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
    

}
