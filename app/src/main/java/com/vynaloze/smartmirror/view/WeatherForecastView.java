package com.vynaloze.smartmirror.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.TextView;

import com.vynaloze.smartmirror.R;

public class WeatherForecastView extends ConstraintLayout {
    private WebView forecastIcon;
    private TextView dayOfWeek;
    private TextView upperTemperature;
    private TextView lowerTemperature;


    public WeatherForecastView(Context context) {
        super(context);
        initializeViews(context);
    }

    public WeatherForecastView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public WeatherForecastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.small_weather_forecast, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        forecastIcon = findViewById(R.id.smallForecastIcon);
        dayOfWeek = findViewById(R.id.smallForecastTextDay);
        upperTemperature = findViewById(R.id.smallForecastUpperTemperature);
        lowerTemperature = findViewById(R.id.smallForecastLowerTemperature);
    }

    public WebView getForecastIcon() {
        return forecastIcon;
    }

    public TextView getDayOfWeek() {
        return dayOfWeek;
    }

    public TextView getUpperTemperature() {
        return upperTemperature;
    }

    public TextView getLowerTemperature() {
        return lowerTemperature;
    }
}
