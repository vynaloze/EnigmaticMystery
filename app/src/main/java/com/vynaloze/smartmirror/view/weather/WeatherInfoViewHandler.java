package com.vynaloze.smartmirror.view.weather;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vynaloze.smartmirror.model.weather.pojo.Weather;

public class WeatherInfoViewHandler implements ViewHandler {
    private WeatherInfoView view;

    public WeatherInfoViewHandler(WeatherInfoView view) {
        this.view = view;
    }

    @Override
    public void updateData(Weather weather) {
        prepareIcon(view.getWeatherIcon(), weather.getIcon());
        view.getTemperature().setText(weather.getTemperature() + "°C");
    }

    public void updateWeatherComment(String comment) {
        view.getTextComment().getCurrentView().setSelected(false);              // disabling scrolling for future iteration
        view.getTextComment().setText(comment);
        view.getTextComment().getCurrentView().postDelayed(new Runnable() {     // wait 3 sec and start scrolling
            @Override
            public void run() {
                view.getTextComment().getCurrentView().setSelected(true);
            }
        }, 3000);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void prepareIcon(WebView icon, String weatherIconType) {
        icon.getSettings().setJavaScriptEnabled(true);
        icon.setLayerType(View.LAYER_TYPE_SOFTWARE, null);  //disabled hardware acceleration.. strangely, it significantly improves performance
        icon.loadUrl("file:///android_asset/largeWeatherImage.html");
        icon.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:set_icon_type('" + weatherIconType + "')");
            }
        });
    }
}
