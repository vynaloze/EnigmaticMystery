package com.vynaloze.smartmirror.view.weather;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Map;

public class WeatherInfoViewHandler {
    private WeatherInfoView view;

    public WeatherInfoViewHandler(WeatherInfoView view) {
        this.view = view;
    }

    public void updateData(Map<String, String> forecast) {
        prepareIcon(view.getWeatherIcon(), forecast);
        view.getTemperature().setText(forecast.get("temperature") + "°C");
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void prepareIcon(WebView icon, Map<String, String> forecast) {
        icon.getSettings().setJavaScriptEnabled(true);
        icon.setLayerType(View.LAYER_TYPE_SOFTWARE, null);  //disabled hardware acceleration.. strangely, it significantly improves performance
        icon.loadUrl("file:///android_asset/largeWeatherImage.html");
        icon.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:set_icon_type('" + forecast.get("icon") + "')");
            }
        });
    }
}
