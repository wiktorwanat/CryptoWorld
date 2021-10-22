package com.wwanat.CryptoWorld.Reports;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;

public interface CryptocurrencyAnalysis {

    double calculateRSI(Cryptocurrency cryptocurrency);

    double calculateMRSI(Cryptocurrency cryptocurrency, double RSI);
}

