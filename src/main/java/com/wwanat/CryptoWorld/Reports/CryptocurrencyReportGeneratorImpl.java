package com.wwanat.CryptoWorld.Reports;

import com.wwanat.CryptoWorld.Mail.MailService;
import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.CryptocurrencyHistoricalValue;
import com.wwanat.CryptoWorld.Model.Types.HistoricalValue;
import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Tools.ChartGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class CryptocurrencyReportGeneratorImpl implements CryptocurrencyReportGenerator {

    Logger logger = LoggerFactory.getLogger(CryptocurrencyReportGeneratorImpl.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private ChartGenerator chartGenerator;

    @Override
    public Report generateCryptocurrencyReport(User user, Cryptocurrency cryptocurrency) {
        Report cryptocurrencyReport = null;
        if (cryptocurrency != null) {
            double actualRSI = calculateRSI(cryptocurrency);
            double actualMA = calculateMA(cryptocurrency);
            double actualSMA = calculateSMA(cryptocurrency);
            String chartPath = chartGenerator.generateCryptocurrencyChart(cryptocurrency);
            if (chartPath != null) {
                cryptocurrencyReport.setChartpath(chartPath);
                //cryptocurrencyReport.setMA(actualMA);
                //cryptocurrencyReport.setRSI(actualRSI);
                //cryptocurrencyReport.setSMA(actualSMA);
                logger.info("Cryptucrrency report generated successfully",CryptocurrencyReportGeneratorImpl.class);
                try {
                    mailService.sendReportMail(user, cryptocurrency, cryptocurrencyReport);
                }catch(MessagingException messagingException){
                    logger.error("Exception thrown - report sending failed", CryptocurrencyReportGeneratorImpl.class);
                }
            }
        }else{
            logger.warn("Given cryptocurrency object is nullable",CryptocurrencyReportGeneratorImpl.class);
        }
        return cryptocurrencyReport;
    }

    private double calculateRSI(Cryptocurrency cryptocurrency) {
        double RSI = 0.0;
        if (cryptocurrency != null) {
            if (cryptocurrency.getCryptocurrencyHistoricalValue() != null) {
                CryptocurrencyHistoricalValue historicalValueObject = cryptocurrency.getCryptocurrencyHistoricalValue();
                List<HistoricalValue> cryptocurrencyHistoricalValueList = historicalValueObject.getHistoricalValues();

                double RS = 0.0;
                double averagePriceGrowthOnClose = 0.0;
                double averagePriceDropOnClose = 0.0;
                RSI = 100.0 - (100.0 / (1.0 + RS));
            }
        }
        return RSI;
    }


    private double calculateSMA(Cryptocurrency cryptocurrency) {
        double SMA = 0.0;
        return SMA;
    }

    private double calculateMA(Cryptocurrency cryptocurrency) {
        return 0;
    }


    private double[] calculateAveragePriceGrowthOnClose(List<HistoricalValue> historicalValues) {
        double[] averagePriceGrowthOnClose = new double[14];
        for (int a = 14; a < historicalValues.size(); a++) {
            averagePriceGrowthOnClose[a - 1] = historicalValues.get(a - 1).value - historicalValues.get(a).value;
        }
        return averagePriceGrowthOnClose;
    }

    private double calculateAveragePriceDropOnClose(List<HistoricalValue> historicalValues) {
        double averagePriceDropOnClose = 0.0;

        return averagePriceDropOnClose;
    }
}
