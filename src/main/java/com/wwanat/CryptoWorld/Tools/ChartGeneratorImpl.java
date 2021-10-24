package com.wwanat.CryptoWorld.Tools;

import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.CryptocurrencyHistoricalValue;
import com.wwanat.CryptoWorld.Model.Types.HistoricalValue;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ChartGeneratorImpl implements ChartGenerator{

    private Logger logger = LoggerFactory.getLogger(ChartGeneratorImpl.class);

    @Value("${chart.path}")
    private String chartPath;

    @Override
    public String generateCryptocurrencyChart(Cryptocurrency cryptocurrency) {
        String path = null;
        if(cryptocurrency!=null){
            logger.info("Creating chart for " + cryptocurrency.getName(),ChartGeneratorImpl.class);
            XYDataset dataset = createDataset(cryptocurrency.getCryptocurrencyHistoricalValue());
            JFreeChart chart  = createChart(dataset,cryptocurrency);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            try {
                path = chartPath.concat(cryptocurrency.getName()).concat("-"+now).concat(".png");
                ChartUtils.saveChartAsJPEG(new File(path), chart, 450, 400);
                logger.info("Chart saved",ChartGeneratorImpl.class);
            }catch(IOException e){
                logger.error("Cannot create chart in given destination",ChartGeneratorImpl.class);
            }
        }
        return path;
    }

    private JFreeChart createChart(XYDataset dataset, Cryptocurrency cryptocurrency){
        JFreeChart chart = null;
        if(dataset!=null){
            chart = ChartFactory.createXYLineChart(
                    cryptocurrency.getName(),
                    "Time",
                    "Value $",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );
            XYPlot plot = chart.getXYPlot();

            var renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesPaint(0, Color.RED);
            renderer.setSeriesStroke(0, new BasicStroke(2.0f));

            plot.setRenderer(renderer);
            plot.setBackgroundPaint(Color.white);

            plot.setRangeGridlinesVisible(true);
            plot.setRangeGridlinePaint(Color.BLACK);

            plot.setDomainGridlinesVisible(true);
            plot.setDomainGridlinePaint(Color.BLACK);

            chart.getLegend().setFrame(BlockBorder.NONE);

            chart.setTitle(new TextTitle(cryptocurrency.getName()+ " value",
                            new Font("Serif", java.awt.Font.BOLD, 18)
                    )
            );
            logger.info("Chart created successfully",ChartGeneratorImpl.class);
        }
        return chart;
    }

    private XYDataset createDataset(CryptocurrencyHistoricalValue cryptocurrencyHistoricalValue){
        TimeSeriesCollection dataset = null;
        if(cryptocurrencyHistoricalValue!=null){
            TimeSeries series = new TimeSeries("Value");
            List<HistoricalValue> historicalValueList= cryptocurrencyHistoricalValue.getHistoricalValues();
            for(HistoricalValue historicalValue: historicalValueList){
                series.add(new Day(convertStringIntoDate(historicalValue.time)),historicalValue.value);
            }
        }
        return dataset;
    }

    private Date convertStringIntoDate(String time){
        Date date = null;
        if(time!=null){
            time = time.replace("T"," ");
            time = time.replace("Z","");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = format.parse(time);
            }catch(ParseException e){
                logger.error("Parse exception thrown in convertStringIntoDate method",ChartGeneratorImpl.class);
            }
        }
        return date;
    }

}
