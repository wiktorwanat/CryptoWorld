package com.wwanat.CryptoWorld.Mail;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Reports.Report;


public class ReportMailForm {

    public static final String reportMailTitle = "CryptoWorld Report!";

    private static String reportMailContent = "<h1>CryptoWorld Report for %s</h1>" +
            "<img class='height: 20px ; width: 20px' src='%s'>" +
            "<h5 style='text-align: center;' >Actual Price: %.2f $</h5>" +
            "<h5 style='text-align: center;' >MarketCap: %.2f $</h5>" +
            "<h5 style='text-align: center;' >Volume24h: %.2f %%</h5>" +
            "<h5 style='text-align: center;' >1h change: %.2f %%</h5>" +
            "<h5 style='text-align: center;' >24h change: %.2f %%</h5>" +
            "<h5 style='text-align: center;' >7d change: %.2f %%</h5>" +
            "<p></p>" +
            "<h5>%s details:</h5>" +
            "<h4>%s</h4>" +
            "<h4>Source code: %s</h4>" +
            "<h4>Website: %s</h4>" +
            "<h4>Technical documentation: %s</h4>" +
            "<h4>Date of creation: %s</h4>" +
            "<p><p>" +
            "<h5>Technical indicators:</h5>" +
            "<h5>Relative Strength Index (RSI) : %.2f</h5>" +
            "<h5>Moving Averages (MA) : %.2f</h5>" +
            "<h5>Simple Moving Averages (SMA) : %.2f</h5>";


    public static String getReportEmailContent(Cryptocurrency cryptocurrency, Report report) {
        if (cryptocurrency != null) {
            reportMailContent = String.format(reportMailContent, cryptocurrency.getCryptocurrencyDetails().getLogoUrl(), cryptocurrency.getName(), cryptocurrency.getActualPrice(), cryptocurrency.getMarketCap(),
                    cryptocurrency.getVolume24h(), cryptocurrency.getPercent_change_1h(), cryptocurrency.getPercent_change_24h(), cryptocurrency.getPercent_change_7d(),
                    cryptocurrency.getName(), cryptocurrency.getCryptocurrencyDetails().getDescription(), cryptocurrency.getCryptocurrencyDetails().getSourceCodeUrl(),
                    cryptocurrency.getCryptocurrencyDetails().getWebsiteUrl(), cryptocurrency.getCryptocurrencyDetails().getTechnicalDocumentationUrl(), cryptocurrency.getCryptocurrencyDetails().getDateOfCreation(),
                    report.getRSI(), report.getMA(), report.getSMA());
        }
        return reportMailContent;
    }
}
