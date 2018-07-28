package com.vynaloze.smartmirror.view.weather.graph;

import com.annimon.stream.Stream;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.vynaloze.smartmirror.model.weather.pojo.Weather;

import java.util.List;

public class WeatherToBarDataParser {
    public static BarDataSet toDataSet(Weather weather) {
        List<BarEntry> list = Stream.of(weather.getPrecipProbability().entrySet())
                .map(entry -> new BarEntry(entry.getKey(), entry.getValue()))
                .toList();
        return new BarDataSet(list, "BarDataSet");
    }
}
