package com.vynaloze.smartmirror.model.randomcomment.pojo;

import android.support.annotation.NonNull;

public class RandomComment {
    @NonNull
    private String text;
    private double probability;
    @NonNull
    private PartOfDay partOfDay;
    @NonNull
    private WeatherType weatherType;

    public RandomComment(@NonNull String text, double probability, @NonNull PartOfDay partOfDay, @NonNull WeatherType weatherType) {
        this.text = text.replace("\\n", System.getProperty("line.separator"));
        this.probability = probability;
        this.partOfDay = partOfDay;
        this.weatherType = weatherType;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public double getProbability() {
        return probability;
    }

    @NonNull
    public PartOfDay getPartOfDay() {
        return partOfDay;
    }

    @NonNull
    public WeatherType getWeatherType() {
        return weatherType;
    }

    @Override
    public String toString() {
        return "RandomComment{" +
                "text='" + text + '\'' +
                ", probability=" + probability +
                ", partOfDay=" + partOfDay +
                ", weatherType=" + weatherType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RandomComment)) return false;

        RandomComment that = (RandomComment) o;

        if (Double.compare(that.probability, probability) != 0) return false;
        if (!text.equals(that.text)) return false;
        if (partOfDay != that.partOfDay) return false;
        return weatherType == that.weatherType;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = text.hashCode();
        temp = Double.doubleToLongBits(probability);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + partOfDay.hashCode();
        result = 31 * result + weatherType.hashCode();
        return result;
    }
}
