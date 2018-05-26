package com.vynaloze.smartmirror.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.annimon.stream.Stream;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
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
    private MutableLiveData<BarGraphSeries<DataPoint>> series;

    public LiveData<BarGraphSeries<DataPoint>> getSeries() {
        if (series == null) {
            series = new MutableLiveData<>();
            fetchData();
        }
        return series;
    }

    private void fetchData() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                forecastRequester.request(new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject forecast) {
                        List<DataPoint> list = WeatherJSONParser.parsePrecipProbability(forecast);
                        DataPoint[] dataPoints = Stream.of(list).toArray(DataPoint[]::new);
                        series.postValue(new BarGraphSeries<>(dataPoints));
                        Log.d(TAG, "Updated weather");
                    }
                });
                Log.d(TAG, "Sent request");
            }
        }, 0, 15, TimeUnit.MINUTES);
    }
}
