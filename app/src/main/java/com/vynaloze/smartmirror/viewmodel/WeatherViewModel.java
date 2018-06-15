package com.vynaloze.smartmirror.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.vynaloze.smartmirror.model.weather.web.ForecastRequester;
import com.vynaloze.smartmirror.model.weather.web.VolleyCallback;
import com.vynaloze.smartmirror.model.weather.web.WeatherJSONParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WeatherViewModel extends ViewModel {
    private static final String TAG = "WeatherViewModel";
    private ForecastRequester forecastRequester = new ForecastRequester();
    private MutableLiveData<BarDataSet> precipProbabilityDataSet;
    private MutableLiveData<Map<String, String>> currentWeatherInfo;
    private MutableLiveData<List<Map<String, String>>> dailyForecast;
    private List<String> weatherComments;
    private MutableLiveData<String> currentWeatherComment;

    public WeatherViewModel() {
        precipProbabilityDataSet = new MutableLiveData<>();
        currentWeatherInfo = new MutableLiveData<>();
        dailyForecast = new MutableLiveData<>();
        weatherComments = new ArrayList<>();
        currentWeatherComment = new MutableLiveData<>();
        fetchData();
    }

    public LiveData<BarDataSet> getPrecipProbabilityDataSet() {
        return precipProbabilityDataSet;
    }

    public LiveData<Map<String, String>> getCurrentWeatherInfo() {
        return currentWeatherInfo;
    }

    public LiveData<List<Map<String, String>>> getDailyForecast() {
        return dailyForecast;
    }

    public LiveData<String> getCurrentWeatherComment() {
        return currentWeatherComment;
    }

    private void fetchData() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                forecastRequester.request(new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject forecast) {
                        List<BarEntry> entryList = WeatherJSONParser.parsePrecipProbability(forecast);
                        precipProbabilityDataSet.postValue(new BarDataSet(entryList, "BarDataSet"));

                        Map<String, String> current = WeatherJSONParser.parseCurrentWeatherInfo(forecast);
                        currentWeatherInfo.postValue(current);

                        List<Map<String, String>> daily = WeatherJSONParser.parseDailyForecast(forecast);
                        dailyForecast.postValue(daily);

                        weatherComments = WeatherJSONParser.parseWeatherComments(forecast);

                        Log.d(TAG, "Updated weather");
                    }
                });
                Log.d(TAG, "Sent request");
            }
        }, 0, 15, TimeUnit.MINUTES);

        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (currentWeatherComment.getValue() == null) {             //todo new class 4 that?
                    currentWeatherComment.postValue(weatherComments.get(0));
                } else {
                    int currentlyDisplayed = weatherComments.indexOf(currentWeatherComment.getValue());
                    int nextOne = currentlyDisplayed + 1;
                    if (nextOne < weatherComments.size()) {
                        currentWeatherComment.postValue(weatherComments.get(nextOne));
                    } else {
                        currentWeatherComment.postValue(weatherComments.get(0));
                    }
                }

            }
        }, 5, 23, TimeUnit.SECONDS);
    }
}
