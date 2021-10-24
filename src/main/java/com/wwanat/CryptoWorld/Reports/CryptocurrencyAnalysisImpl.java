package com.wwanat.CryptoWorld.Reports;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.CryptocurrencyHistoricalValue;
import com.wwanat.CryptoWorld.Model.Types.HistoricalValue;

import java.util.ArrayList;
import java.util.List;

public class CryptocurrencyAnalysisImpl implements CryptocurrencyAnalysis{

    @Override
    public double calculateRSI(Cryptocurrency cryptocurrency) {
        double RSI = 0.0;
        if(cryptocurrency!=null){
            if(cryptocurrency.getCryptocurrencyHistoricalValue()!=null){
                CryptocurrencyHistoricalValue historicalValueObject = cryptocurrency.getCryptocurrencyHistoricalValue();
                List<HistoricalValue> cryptocurrencyHistoricalValueList = historicalValueObject.getHistoricalValues();

                double RS = 0.0;
                double averagePriceGrowthOnClose = 0.0;
                double averagePriceDropOnClose = 0.0;
                RSI = 100.0 - (100.0/(1.0+RS));
            }
        }
        return RSI;
    }

    @Override
    public double calculateMRSI(Cryptocurrency cryptocurrency, double RSI) {
        return 0;
    }

    @Override
    public double calculateSMA(List<Double> values) {
        double SMA = 0.0;
        if(values!=null){

        }
        return SMA;
    }

    @Override
    public double calculateEMA(List<Double> values) {
        return 0;
    }

    @Override
    public double calculateWSM(List<Double> values) {
        return 0;
    }

    private double[] calculateAveragePriceGrowthOnClose(List<HistoricalValue> historicalValues){
        double[] averagePriceGrowthOnClose = new double[14];
        for(int a=14;a<historicalValues.size();a++){
            averagePriceGrowthOnClose[a-1] = historicalValues.get(a-1).value-historicalValues.get(a).value;
        }
        return averagePriceGrowthOnClose;
    }

    private double calculateAveragePriceDropOnClose(List<HistoricalValue> historicalValues){
        double averagePriceDropOnClose = 0.0;

        return averagePriceDropOnClose;
    }
}
