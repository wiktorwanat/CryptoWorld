package com.wwanat.CryptoWorld.Model;

import org.jfree.chart.JFreeChart;

public class Report {

    public double RSI;

    public double MA;

    public double SMA;

    public JFreeChart chart;

    public Report(double rsi, double mrsi, double sma, JFreeChart chart) {
        this.chart = chart;
        this.RSI = rsi;
        this.MA = mrsi;
        this.SMA = sma;
    }

}
