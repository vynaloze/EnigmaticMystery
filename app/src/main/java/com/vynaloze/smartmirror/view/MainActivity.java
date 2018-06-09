package com.vynaloze.smartmirror.view;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.vynaloze.smartmirror.R;
import com.vynaloze.smartmirror.view.weather.WeatherForecastView;
import com.vynaloze.smartmirror.view.weather.WeatherForecastViewHandler;
import com.vynaloze.smartmirror.view.weather.WeatherInfoViewHandler;
import com.vynaloze.smartmirror.view.weather.graph.ForecastGraphHandler;
import com.vynaloze.smartmirror.viewmodel.CalendarViewModel;
import com.vynaloze.smartmirror.viewmodel.WeatherViewModel;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends FragmentActivity {

    TextView calendarTextView; //todo ui to classes (really??)
    BarChart forecastGraph;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUI();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //stop screen from dimming
        setContentView(R.layout.activity_main);

        // calendar
        calendarTextView = findViewById(R.id.calendarTextView);
        CalendarViewModel calendarViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        calendarViewModel.getDate().observe(this, date -> calendarTextView.setText(date));

        // forecast graph
        forecastGraph = findViewById(R.id.forecastGraph);
        ForecastGraphHandler graphHandler = new ForecastGraphHandler(forecastGraph);
        WeatherViewModel weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.getPrecipProbabilityDataSet().observe(this, graphHandler::updateData);

        // current weather
        WeatherInfoViewHandler weatherInfoViewHandler = new WeatherInfoViewHandler(findViewById(R.id.weatherInfoView));
        weatherViewModel.getCurrentWeatherInfo().observe(this, weatherInfoViewHandler::updateData);
        weatherViewModel.getCurrentWeatherComment().observe(this, weatherInfoViewHandler::updateWeatherComment);

        // 3-day forecast
        List<WeatherForecastView> dailyForecast = Arrays.asList(
                findViewById(R.id.weatherForecastView1),
                findViewById(R.id.weatherForecastView2),
                findViewById(R.id.weatherForecastView3)
        );
        WeatherForecastViewHandler weatherForecastViewHandler = new WeatherForecastViewHandler(dailyForecast);
        weatherViewModel.getDailyForecast().observe(this, weatherForecastViewHandler::updateData);

    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

}
