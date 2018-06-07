package com.vynaloze.smartmirror.view.weather;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.TextView;

import com.vynaloze.smartmirror.R;

public class WeatherInfoView extends ConstraintLayout {
    private WebView weatherIcon;
    private TextView temperature;

    public WeatherInfoView(Context context) {
        super(context);
        initializeViews(context);
    }

    public WeatherInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public WeatherInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.weather_info, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        weatherIcon = findViewById(R.id.weatherInfoImage);
        temperature = findViewById(R.id.currentTemperature);
    }

    public WebView getWeatherIcon() {
        return weatherIcon;
    }

    public TextView getTemperature() {
        return temperature;
    }
}
