package com.wwanat.CryptoWorld.Model;

import com.wwanat.CryptoWorld.Model.Types.HistoricalValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.List;

@Document(collection = "cryptocurrency_historical_values")
public class CryptocurrencyHistoricalValue {

    @Id
    private String id;
    private List<HistoricalValue> historicalValues;

    public CryptocurrencyHistoricalValue() {
        this.id=null;
        this.historicalValues = new ArrayList<HistoricalValue>();
    }

    public CryptocurrencyHistoricalValue(List<HistoricalValue> historicalValues) {
        super();
        this.historicalValues = historicalValues;
    }

    public CryptocurrencyHistoricalValue(String id, List<HistoricalValue> historicalValues) {
        super();
        this.id = id;
        this.historicalValues = historicalValues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<HistoricalValue> getHistoricalValues() {
        return historicalValues;
    }

    public void setHistoricalValues(List<HistoricalValue> historicalValues) {
        this.historicalValues = historicalValues;
    }

    public List<HistoricalValue> addHistoricalValueToList(HistoricalValue historicalValue){
        if(this.historicalValues!=null) {
            this.historicalValues.add(historicalValue);
        }
        return getHistoricalValues();
    }

    @Override
    public String toString() {
        return "CryptocurrencyHistoricalValue{" +
                "id='" + id + '\'' +
                ", historicalValues=" + historicalValues +
                '}';
    }
}
