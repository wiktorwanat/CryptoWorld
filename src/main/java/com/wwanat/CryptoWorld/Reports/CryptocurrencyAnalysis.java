package com.wwanat.CryptoWorld.Reports;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;

import java.util.List;

public interface CryptocurrencyAnalysis {

    double calculateSMA(List<Double> values);

    double calculateEMA(List<Double> values);

    double calculateWSM(List<Double> values);

    double calculateRSI(Cryptocurrency cryptocurrency);

    double calculateMRSI(Cryptocurrency cryptocurrency, double RSI);
}

