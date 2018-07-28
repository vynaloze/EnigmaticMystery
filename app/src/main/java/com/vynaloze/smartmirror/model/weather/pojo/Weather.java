package com.vynaloze.smartmirror.model.weather.pojo;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Weather {
    private int temperature;
    @NonNull
    private String precipType;
    @NonNull
    private Map<Float, Float> precipProbability;
    @NonNull
    private String icon;
    @NonNull
    private List<String> summaries;
    @NonNull
    private List<DailyForecast> dailyForecasts;

    public int getTemperature() {
        return temperature;
    }

    @NonNull
    public String getPrecipType() {
        return precipType;
    }

    @NonNull
    public Map<Float, Float> getPrecipProbability() {
        return precipProbability;
    }

    @NonNull
    public String getIcon() {
        return icon;
    }

    @NonNull
    public List<String> getSummaries() {
        return summaries;
    }

    @NonNull
    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }


    public static class DailyForecast {
        @NonNull
        private String icon;
        private int temperatureLow;
        private int temperatureHigh;

        public DailyForecast(@NonNull String icon, int temperatureLow, int temperatureHigh) {
            this.icon = icon;
            this.temperatureLow = temperatureLow;
            this.temperatureHigh = temperatureHigh;
        }

        @NonNull
        public String getIcon() {
            return icon;
        }

        public int getTemperatureLow() {
            return temperatureLow;
        }

        public int getTemperatureHigh() {
            return temperatureHigh;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DailyForecast)) return false;

            DailyForecast that = (DailyForecast) o;

            if (temperatureLow != that.temperatureLow) return false;
            if (temperatureHigh != that.temperatureHigh) return false;
            return icon.equals(that.icon);
        }

        @Override
        public int hashCode() {
            int result = icon.hashCode();
            result = 31 * result + temperatureLow;
            result = 31 * result + temperatureHigh;
            return result;
        }
    }


    public static class Builder {
        private int temperature;
        @NonNull
        private String precipType;
        @NonNull
        private Map<Float, Float> precipProbability;
        @NonNull
        private String icon;
        @NonNull
        private List<String> summaries = new ArrayList<>();
        @NonNull
        private List<DailyForecast> dailyForecasts = new ArrayList<>();

        public void temperature(int temperature) {
            this.temperature = temperature;
        }

        public void precipType(@NonNull String precipType) {
            this.precipType = precipType;
        }

        public void precipProbability(@NonNull Map<Float, Float> precipProbability) {
            this.precipProbability = precipProbability;
        }

        public void icon(@NonNull String icon) {
            this.icon = icon;
        }

        public void summary(@NonNull String summary) {
            this.summaries.add(summary);
        }

        public void dailyForecast(@NonNull DailyForecast dailyForecast) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weather)) return false;

        Weather weather = (Weather) o;

        if (temperature != weather.temperature) return false;
        if (!precipType.equals(weather.precipType)) return false;
        if (!precipProbability.equals(weather.precipProbability)) return false;
        if (!icon.equals(weather.icon)) return false;
        if (!summaries.equals(weather.summaries)) return false;
        return dailyForecasts.equals(weather.dailyForecasts);
    }

    @Override
    public int hashCode() {
        int result = temperature;
        result = 31 * result + precipType.hashCode();
        result = 31 * result + precipProbability.hashCode();
        result = 31 * result + icon.hashCode();
        result = 31 * result + summaries.hashCode();
        result = 31 * result + dailyForecasts.hashCode();
        return result;
    }
}
