package com.vynaloze.smartmirror.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.vynaloze.smartmirror.R;
import com.vynaloze.smartmirror.view.graph.ForecastGraphHandler;
import com.vynaloze.smartmirror.viewmodel.CalendarViewModel;
import com.vynaloze.smartmirror.viewmodel.WeatherViewModel;


public class MainActivity extends FragmentActivity {

    TextView calendarTextView; //todo ui to classes
    BarChart forecastGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUI();
        setContentView(R.layout.activity_main);

        calendarTextView = findViewById(R.id.calendarTextView);
        forecastGraph = findViewById(R.id.forecastGraph);

        ForecastGraphHandler graphHandler = new ForecastGraphHandler(forecastGraph);

        CalendarViewModel calendarViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        calendarViewModel.getDate().observe(this, date -> calendarTextView.setText(date));

        WeatherViewModel weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.getPrecipProbabilityDataSet().observe(this, graphHandler::updateData);

        WebView currentWeatherImage = findViewById(R.id.currentWeatherImage);
        TextView currentTemperature = findViewById(R.id.currentTemperature);
        currentWeatherImage.getSettings().setJavaScriptEnabled(true);
        currentWeatherImage.loadUrl("file:///android_asset/weatherImage.html");

        weatherViewModel.getCurrentConditions().observe(this, map -> {
            currentWeatherImage.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    view.loadUrl("javascript:set_icon_type('" + map.get("icon") + "')");
                }
            });
            currentTemperature.setText(map.get("temperature") + "°C");
        });

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
