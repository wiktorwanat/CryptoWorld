package com.wwanat.CryptoWorld.Reports;

public class Report {

    private double RSI;

    private double MA;

    private double SMA;

    public String chartpath;

    public Report() {
        this.MA = 0.0;
        this.RSI = 0.0;
        this.SMA = 0.0;
    }

    public Report(double rsi, double mrsi, double sma, String chart) {
        this.RSI = rsi;
        this.MA = mrsi;
        this.SMA = sma;
        this.chartpath = chart;
    }

    public double getRSI() {
        return RSI;
    }

    public void setRSI(double RSI) {
        this.RSI = RSI;
    }

    public double getMA() {
        return MA;
    }

    public void setMA(double MA) {
        this.MA = MA;
    }

    public double getSMA() {
        return SMA;
    }

    public void setSMA(double SMA) {
        this.SMA = SMA;
    }

    public String getChartpath() {
        return chartpath;
    }

    public void setChartpath(String chartpath) {
        this.chartpath = chartpath;
    }
}
