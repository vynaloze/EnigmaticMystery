package com.vynaloze.smartmirror.model.randomcomment.pojo;

import com.vynaloze.smartmirror.model.weather.pojo.Weather;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum WeatherType {
    ALL, RAINY, SNOWY, HOT, COLD;
    private static final int TOO_HOT = 27;
    private static final int TOO_COLD = 0;

    public static Set<WeatherType> getTypesOf(Weather weather) {
        if (weather == null) {
            return Collections.singleton(ALL);
        }
        Set<WeatherType> types = new HashSet<>();
        switch (weather.getPrecipType()) {
            case "rain":
                types.add(RAINY);
                break;
            case "snow":
            case "sleet":
                types.add(SNOWY);
            default:
                break;
        }

        if (weather.getTemperature() >= TOO_HOT) {
            types.add(HOT);
        }
        if (weather.getTemperature() <= TOO_COLD) {
            types.add(COLD);
        }

        if (types.isEmpty()) {
            types.add(ALL);
        }

        return types;
    }
}
