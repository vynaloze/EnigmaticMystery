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
        List<BarEntry> list = new ArrayList<>();
        try {
            JSONObject hourly = forecast.getJSONObject("hourly");
            JSONArray data = hourly.getJSONArray("data");
            for (int i = 0; i < data.length() / 2; i++) {       // div/2 to have 24h only
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

    public static Map<String, String> parseCurrentConditions(JSONObject forecast) {
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
}
