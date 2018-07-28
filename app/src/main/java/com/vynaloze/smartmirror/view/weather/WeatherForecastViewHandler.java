package com.vynaloze.smartmirror.view.weather;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vynaloze.smartmirror.model.weather.pojo.Weather;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WeatherForecastViewHandler implements ViewHandler {
    private List<WeatherForecastView> viewList;

    public WeatherForecastViewHandler(List<WeatherForecastView> viewList) {
        this.viewList = viewList;
    }

    @Override
    public void updateData(Weather weather) {
        for (int i = 0; i < viewList.size(); i++) {
            WeatherForecastView view = viewList.get(i);
            Weather.DailyForecast forecast = weather.getDailyForecasts().get(i);
            view.getDayOfWeek().setText(getDayOfWeekWithOffset(i + 1));
            prepareIcon(view.getForecastIcon(), forecast.getIcon());
            view.getUpperTemperature().setText(String.valueOf(forecast.getTemperatureHigh()));
            view.getLowerTemperature().setText(String.valueOf(forecast.getTemperatureLow()));
        }
    }

    private String getDayOfWeekWithOffset(int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, offset);
        SimpleDateFormat format = new SimpleDateFormat("EEE", Locale.getDefault());
        return format.format(calendar.getTime());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void prepareIcon(WebView icon, String weatherIconType) {
        icon.getSettings().setJavaScriptEnabled(true);
        icon.setLayerType(View.LAYER_TYPE_SOFTWARE, null);  //disabled hardware acceleration.. strangely, it significantly improves performance
        icon.loadUrl("file:///android_asset/smallWeatherImage.html");
        icon.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:set_icon_type('" + weatherIconType + "')");
            }
        });
    }
}
