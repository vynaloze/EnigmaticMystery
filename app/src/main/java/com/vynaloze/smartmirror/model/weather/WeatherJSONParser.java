package com.vynaloze.smartmirror.model.weather;

import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherJSONParser {
    public static List<DataPoint> parsePrecipProbability(JSONObject forecast) {
        List<DataPoint> list = new ArrayList<>();
        try {
            JSONObject hourly = forecast.getJSONObject("hourly");
            JSONArray data = hourly.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {       //todo: div/2 to 24h only
                JSONObject entry = data.getJSONObject(i);
                list.add(new DataPoint(
                        entry.getLong("time"),
                        entry.getDouble("precipProbability")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
