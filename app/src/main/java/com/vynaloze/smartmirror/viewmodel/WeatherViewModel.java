package com.vynaloze.smartmirror.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.vynaloze.smartmirror.model.weather.pojo.Weather;
import com.vynaloze.smartmirror.model.weather.web.ForecastRequester;
import com.vynaloze.smartmirror.model.weather.web.VolleyCallback;
import com.vynaloze.smartmirror.model.weather.web.WeatherJSONParser;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WeatherViewModel extends ViewModel {
    private static final String TAG = "WeatherViewModel";
    private ForecastRequester forecastRequester = new ForecastRequester();
    private MutableLiveData<Weather> weather;
    private MutableLiveData<String> weatherSummary;

    public WeatherViewModel() {
        weather = new MutableLiveData<>();
        weatherSummary = new MutableLiveData<>();
        fetchData();
    }

    public LiveData<Weather> getWeather() {
        return weather;
    }

    public LiveData<String> getWeatherSummary() {
        return weatherSummary;
    }

    private void fetchData() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                forecastRequester.request(new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject forecast) {
                        weather.postValue(new WeatherJSONParser().parseForecast(forecast));
                        Log.d(TAG, "Updated weather");
                    }
                });
                Log.d(TAG, "Sent request");
            }
        }, 0, 15, TimeUnit.MINUTES);

        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (weather.getValue() == null) {
                    return;
                }
                List<String> weatherSummaries = weather.getValue().getSummaries();
                if (weatherSummary.getValue() == null) {             //todo new class 4 that?
                    weatherSummary.postValue(weatherSummaries.get(0));
                } else {
                    int currentlyDisplayed = weatherSummaries.indexOf(weatherSummary.getValue());
                    int nextOne = currentlyDisplayed + 1;
                    if (nextOne < weatherSummaries.size()) {
                        weatherSummary.postValue(weatherSummaries.get(nextOne));
                    } else {
                        weatherSummary.postValue(weatherSummaries.get(0));
                    }
                }

            }
        }, 5, 23, TimeUnit.SECONDS);
    }
}
