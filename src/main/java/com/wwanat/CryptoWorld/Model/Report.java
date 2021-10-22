package com.wwanat.CryptoWorld.Model;

import org.jfree.chart.JFreeChart;

public class Report {

    public double RSI;

    public double MRSI;

    public JFreeChart chart;

    public Report(double rsi, double mrsi, JFreeChart chart) {
        this.chart = chart;
        this.RSI = rsi;
        this.MRSI = mrsi;
    }

}
