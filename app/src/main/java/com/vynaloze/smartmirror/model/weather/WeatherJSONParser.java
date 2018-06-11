package com.vynaloze.smartmirror.model.weather;

import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherJSONParser {
    public static List<BarEntry> parsePrecipProbability(JSONObject forecast) {
        final int HOURS = 25;
        List<BarEntry> list = new ArrayList<>();
        try {
            JSONObject hourly = forecast.getJSONObject("hourly");
            JSONArray data = hourly.getJSONArray("data");
            for (int i = 0; i < HOURS; i++) {
                JSONObject entry = data.getJSONObject(i);
                list.add(new BarEntry(
                        (float) i,
                        (float) entry.getDouble("precipProbability")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Map<String, String> parseCurrentWeatherInfo(JSONObject forecast) {
        Map<String, String> map = new HashMap<>();
        try {
            JSONObject currently = forecast.getJSONObject("currently");
            map.put("icon", currently.getString("icon"));
            map.put("temperature", String.valueOf((int) currently.getDouble("temperature")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static List<Map<String, String>> parseDailyForecast(JSONObject forecast) {
        final int DAYS = 5;
        List<Map<String, String>> list = new ArrayList<>();
        try {
            JSONObject daily = forecast.getJSONObject("daily");
            JSONArray data = daily.getJSONArray("data");
            for (int i = 1; i <= DAYS; i++) {           // starting with 1 to skip today
                JSONObject entry = data.getJSONObject(i);
                Map<String, String> map = new HashMap<>();
                map.put("icon", entry.getString("icon"));
                map.put("temperatureHigh", String.valueOf((int) entry.getDouble("temperatureHigh")));
                map.put("temperatureLow", String.valueOf((int) entry.getDouble("temperatureLow")));
                list.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> parseWeatherComments(JSONObject forecast) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject hourly = forecast.getJSONObject("hourly");
            list.add(hourly.getString("summary"));
            JSONObject daily = forecast.getJSONObject("daily");
            list.add(daily.getString("summary"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
