package com.vynaloze.smartmirror.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.vynaloze.smartmirror.model.weather.WeatherJSONParser;
import com.vynaloze.smartmirror.model.weather.web.ForecastRequester;
import com.vynaloze.smartmirror.model.weather.web.VolleyCallback;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WeatherViewModel extends ViewModel {
    private static final String TAG = "WeatherViewModel";
    private ForecastRequester forecastRequester = new ForecastRequester();
    private MutableLiveData<BarDataSet> dataSet;

    public LiveData<BarDataSet> getDataSet() {
        if (dataSet == null) {
            dataSet = new MutableLiveData<>();
            fetchData();
        }
        return dataSet;
    }

    private void fetchData() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                forecastRequester.request(new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject forecast) {
                        List<BarEntry> entryList = WeatherJSONParser.parsePrecipProbability(forecast);
                        dataSet.postValue(new BarDataSet(entryList, "BarDataSet"));
                        Log.d(TAG, "Updated weather");
                    }
                });
                Log.d(TAG, "Sent request");
            }
        }, 0, 15, TimeUnit.MINUTES);
    }
}
