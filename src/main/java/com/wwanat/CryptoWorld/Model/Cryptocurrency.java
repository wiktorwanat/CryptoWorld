/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Wiktor
 */
@Document(collection="crypto")
public class Cryptocurrency {
    
    @Id
    private String id;
    private String name;
    private double actualPrice;
    private double minDailyPrice;
    private double maxDailyPrice;

    public Cryptocurrency() {
        this.id=null;
        this.name=null;
        this.actualPrice=0.0;
        this.maxDailyPrice=0.0;
        this.minDailyPrice=0.0;
    }

    public Cryptocurrency( String name, double actualPrice, double minDailyPrice, double maxDailyPrice) {
        this.name = name;
        this.actualPrice = actualPrice;
        this.minDailyPrice = minDailyPrice;
        this.maxDailyPrice = maxDailyPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public double getMinDailyPrice() {
        return minDailyPrice;
    }

    public void setMinDailyPrice(double minDailyPrice) {
        this.minDailyPrice = minDailyPrice;
    }

    public double getMaxDailyPrice() {
        return maxDailyPrice;
    }

    public void setMaxDailyPrice(double maxDailyPrice) {
        this.maxDailyPrice = maxDailyPrice;
    }
    
    
    
}
