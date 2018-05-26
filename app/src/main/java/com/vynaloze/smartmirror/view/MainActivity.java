package com.vynaloze.smartmirror.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.vynaloze.smartmirror.R;
import com.vynaloze.smartmirror.viewmodel.CalendarViewModel;
import com.vynaloze.smartmirror.viewmodel.WeatherViewModel;


public class MainActivity extends FragmentActivity {

    TextView calendarTextView; //todo ui to classes
    GraphView forecastGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarTextView = findViewById(R.id.calendarTextView);
        forecastGraph = findViewById(R.id.forecastGraph);

        CalendarViewModel calendarViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        calendarViewModel.getDate().observe(this, date -> calendarTextView.setText(date));

        WeatherViewModel weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.getSeries().observe(this, series -> ForecastGraphHandler.handle(forecastGraph, series));

    }
}
