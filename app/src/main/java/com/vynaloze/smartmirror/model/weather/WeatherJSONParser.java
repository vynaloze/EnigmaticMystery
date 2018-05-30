package com.vynaloze.smartmirror.model.weather;

import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
}
