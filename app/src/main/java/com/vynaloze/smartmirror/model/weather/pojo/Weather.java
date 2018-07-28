package com.vynaloze.smartmirror.model.weather.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Weather {
    private int temperature;
    private String precipType;
    private Map<Float, Float> precipProbability;
    private String icon;
    private List<String> summaries;
    private List<DailyForecast> dailyForecasts;

    public int getTemperature() {
        return temperature;
    }

    public String getPrecipType() {
        return precipType;
    }

    public Map<Float, Float> getPrecipProbability() {
        return precipProbability;
    }

    public String getIcon() {
        return icon;
    }

    public List<String> getSummaries() {
        return summaries;
    }

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }


    public static class DailyForecast {
        private String icon;
        private int temperatureLow;
        private int temperatureHigh;

        public DailyForecast(String icon, int temperatureLow, int temperatureHigh) {
            this.icon = icon;
            this.temperatureLow = temperatureLow;
            this.temperatureHigh = temperatureHigh;
        }

        public String getIcon() {
            return icon;
        }

        public int getTemperatureLow() {
            return temperatureLow;
        }

        public int getTemperatureHigh() {
            return temperatureHigh;
        }

    }


    public static class Builder {
        private int temperature;
        private String precipType;
        private Map<Float, Float> precipProbability;
        private String icon;
        private List<String> summaries = new ArrayList<>();
        private List<DailyForecast> dailyForecasts = new ArrayList<>();

        public void temperature(int temperature) {
            this.temperature = temperature;
        }

        public void precipType(String precipType) {
            this.precipType = precipType;
        }

        public void precipProbability(Map<Float, Float> precipProbability) {
            this.precipProbability = precipProbability;
        }

        public void icon(String icon) {
            this.icon = icon;
        }

        public void summary(String summary) {
            this.summaries.add(summary);
        }

        public void dailyForecast(DailyForecast dailyForecast) {
            this.dailyForecasts.add(dailyForecast);
        }

        public Weather build() {
            return new Weather(this);
        }
    }

    private Weather(Builder builder) {
        this.temperature = builder.temperature;
        this.precipType = builder.precipType;
        this.precipProbability = builder.precipProbability;
        this.icon = builder.icon;
        this.summaries = builder.summaries;
        this.dailyForecasts = builder.dailyForecasts;
    }
}
