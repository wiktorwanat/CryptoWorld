package com.wwanat.CryptoWorld.Reports;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.User;

public interface CryptocurrencyReportGenerator {

    Report generateCryptocurrencyReport(User user, Cryptocurrency cryptocurrency);

}

