package com.wwanat.CryptoWorld.Service;

import com.wwanat.CryptoWorld.Model.CryptocurrencyHistoricalValue;

import java.util.Optional;


public interface CryptocurrencyHistoricalValueService {

    public CryptocurrencyHistoricalValue createCryptocurrencyHistoricalValue(CryptocurrencyHistoricalValue cryptocurrencyHistoricalValue) throws Exception;

    public void removeCryptocurrencyHistoricalValue(CryptocurrencyHistoricalValue cryptocurrencyHistoricalValue) throws Exception;

    public void removeCryptocurrencyHistoricalValueById(String cryptocurrencyHistoricalValueId) throws Exception;

    public CryptocurrencyHistoricalValue updateCryptocurrencyHistoricalValue(CryptocurrencyHistoricalValue cryptocurrencyHistoricalValue) throws Exception;

    public Optional<CryptocurrencyHistoricalValue> getCryptocurrencyHistoricalValueById(String cryptocurrencyHistoricalValueId) throws Exception;

}
