/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wwanat.CryptoWorld.Model;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Wiktor
 */
@Document(collection = "crypto")
public class Cryptocurrency {

    @Id
    private String id;
    private String name;
    private String symbol;
    private String slug;
    private double actualPrice;
    private double marketCap;
    private double percent_change_1h;
    private double percent_change_24h;
    private double percent_change_7d;
    private double volume24h;
    @DBRef
    private CryptocurrencyDetails cryptocurrencyDetails;


    public Cryptocurrency() {
        this.id = null;
        this.name = null;
        this.symbol = null;
        this.slug = null;
        this.actualPrice = 0.0;
        this.marketCap = 0.0;
        this.percent_change_1h = 0.0;
        this.percent_change_24h = 0.0;
        this.percent_change_7d = 0.0;
        this.volume24h = 0.0;
        this.cryptocurrencyDetails = null;
    }

    public Cryptocurrency(String name, String symbol, String slug, double actualPrice, double marketCap, double percent_change_1h, double percent_change_24h, double percent_change_7d, double volume24h) {
        super();
        this.name = name;
        this.symbol = symbol;
        this.slug = slug;
        this.actualPrice = actualPrice;
        this.marketCap = marketCap;
        this.percent_change_1h = percent_change_1h;
        this.percent_change_24h = percent_change_24h;
        this.percent_change_7d = percent_change_7d;
        this.volume24h = volume24h;
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public double getPercent_change_1h() {
        return percent_change_1h;
    }

    public void setPercent_change_1h(double percent_change_1h) {
        this.percent_change_1h = percent_change_1h;
    }

    public double getPercent_change_24h() {
        return percent_change_24h;
    }

    public void setPercent_change_24h(double percent_change_24h) {
        this.percent_change_24h = percent_change_24h;
    }

    public double getPercent_change_7d() {
        return percent_change_7d;
    }

    public void setPercent_change_7d(double percent_change_7d) {
        this.percent_change_7d = percent_change_7d;
    }

    public double getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(double volume24h) {
        this.volume24h = volume24h;
    }

    public CryptocurrencyDetails getCryptocurrencyDetails() {
        return cryptocurrencyDetails;
    }

    public void setCryptocurrencyDetails(CryptocurrencyDetails cryptocurrencyDetails) {
        this.cryptocurrencyDetails = cryptocurrencyDetails;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return "Cryptocurrency{" + "id=" + id + ", name=" + name + ", symbol=" + symbol + ", slug=" + slug + ", actualPrice=" + actualPrice + ", marketCap=" + marketCap + ", percent_change_1h=" + percent_change_1h + ", percent_change_24h=" + percent_change_24h + ", percent_change_7d=" + percent_change_7d + ", volume24h=" + volume24h + ", cryptocurrencyDetails=" + cryptocurrencyDetails + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cryptocurrency other = (Cryptocurrency) obj;
        if (Double.doubleToLongBits(this.actualPrice) != Double.doubleToLongBits(other.actualPrice)) {
            return false;
        }
        if (Double.doubleToLongBits(this.marketCap) != Double.doubleToLongBits(other.marketCap)) {
            return false;
        }
        if (Double.doubleToLongBits(this.percent_change_1h) != Double.doubleToLongBits(other.percent_change_1h)) {
            return false;
        }
        if (Double.doubleToLongBits(this.percent_change_7d) != Double.doubleToLongBits(other.percent_change_7d)) {
            return false;
        }
        if (Double.doubleToLongBits(this.volume24h) != Double.doubleToLongBits(other.volume24h)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.symbol, other.symbol)) {
            return false;
        }
        if (!Objects.equals(this.slug, other.slug)) {
            return false;
        }
        if (!Objects.equals(this.cryptocurrencyDetails, other.cryptocurrencyDetails)) {
            return false;
        }
        return true;
    }


}
