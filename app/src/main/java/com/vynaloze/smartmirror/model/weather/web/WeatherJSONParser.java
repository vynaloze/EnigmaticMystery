package com.vynaloze.smartmirror.model.weather.web;

import com.vynaloze.smartmirror.model.weather.pojo.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WeatherJSONParser {
    private JSONObject forecast;
    private Weather.Builder builder;

    public Weather parseForecast(JSONObject forecast) {
        this.forecast = forecast;
        this.builder = new Weather.Builder();
        parsePrecipProbability();
        parseCurrentWeatherInfo();
        parseDailyForecast();
        parseWeatherComments();
        return builder.build();
    }

    private void parsePrecipProbability() {
        final int HOURS = 25;
        Map<Float, Float> map = new HashMap<>();
        try {
            JSONObject hourly = forecast.getJSONObject("hourly");
            JSONArray data = hourly.getJSONArray("data");
            for (int i = 0; i < HOURS; i++) {
                JSONObject entry = data.getJSONObject(i);
                map.put((float) i, (float) entry.getDouble("precipProbability"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        builder.precipProbability(map);
    }

    private void parseCurrentWeatherInfo() {
        try {
            JSONObject currently = forecast.getJSONObject("currently");
            builder.icon(currently.getString("icon"));
            builder.temperature((int) currently.getDouble("temperature"));
            try {
                builder.precipType(currently.getString("precipType"));
            } catch (JSONException e) {
                builder.precipType("");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseDailyForecast() {
        final int DAYS = 5;
        try {
            JSONObject daily = forecast.getJSONObject("daily");
            JSONArray data = daily.getJSONArray("data");
            for (int i = 1; i <= DAYS; i++) {           // starting with 1 to skip today
                JSONObject entry = data.getJSONObject(i);
                builder.dailyForecast(new Weather.DailyForecast(
                        entry.getString("icon"),
                        (int) entry.getDouble("temperatureLow"),
                        (int) entry.getDouble("temperatureHigh")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseWeatherComments() {
        try {
            JSONObject hourly = forecast.getJSONObject("hourly");
            builder.summary(hourly.getString("summary"));
            JSONObject daily = forecast.getJSONObject("daily");
            builder.summary(daily.getString("summary"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
