package com.vynaloze.smartmirror.view;

import android.annotation.SuppressLint;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WeatherViewHandler {
    private List<WeatherForecastView> viewList;

    public WeatherViewHandler(List<WeatherForecastView> viewList) {
        this.viewList = viewList;
    }

    public void updateData(List<Map<String, String>> forecastList) {
        for (int i = 0; i < viewList.size(); i++) {
            WeatherForecastView view = viewList.get(i);
            Map<String, String> forecast = forecastList.get(i); //todo it better?
            view.getDayOfWeek().setText(getDayOfWeekWithOffset(i + 1));
            prepareIcon(view.getForecastIcon(), forecast);
            view.getUpperTemperature().setText(forecast.get("temperatureHigh"));
            view.getLowerTemperature().setText(forecast.get("temperatureLow"));
        }
    }

    private String getDayOfWeekWithOffset(int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, offset);
        SimpleDateFormat format = new SimpleDateFormat("EEE", Locale.getDefault());
        return format.format(calendar.getTime());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void prepareIcon(WebView icon, Map<String, String> forecast) {
        icon.getSettings().setJavaScriptEnabled(true);
        icon.loadUrl("file:///android_asset/smallWeatherImage.html");
        icon.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:set_icon_type('" + forecast.get("icon") + "')");
            }
        });
    }
}
