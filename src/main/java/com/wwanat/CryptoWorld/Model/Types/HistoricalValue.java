package com.wwanat.CryptoWorld.Model.Types;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;

public class HistoricalValue {
    public String time;
    public Double value;

    public HistoricalValue() {
        this.time = null;
        this.value = 0.0;
    }

    public HistoricalValue(String time, Double value) {
        super();
        this.time = time;
        this.value = value;
    }

    public HistoricalValue(Cryptocurrency cryptocurrency) {
        super();
        if (cryptocurrency != null) {
            if (cryptocurrency.getLastUpdated() != null) {
                if (cryptocurrency.getActualPrice() != 0.0) {
                    this.time = cryptocurrency.getLastUpdated();
                    this.value = cryptocurrency.getActualPrice();
                }
            }
        }
    }
}
