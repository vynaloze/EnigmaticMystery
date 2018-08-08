package com.vynaloze.smartmirror.view;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.vynaloze.smartmirror.R;
import com.vynaloze.smartmirror.view.weather.ViewHandler;
import com.vynaloze.smartmirror.view.weather.WeatherForecastView;
import com.vynaloze.smartmirror.view.weather.WeatherForecastViewHandler;
import com.vynaloze.smartmirror.view.weather.WeatherInfoViewHandler;
import com.vynaloze.smartmirror.view.weather.graph.ForecastGraphHandler;
import com.vynaloze.smartmirror.viewmodel.CalendarViewModel;
import com.vynaloze.smartmirror.viewmodel.RandomCommentViewModel;
import com.vynaloze.smartmirror.viewmodel.WeatherViewModel;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends FragmentActivity {

    TextView calendarTextView; //todo ui to classes (really??)

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


        // random comments
        TextView randomComment = findViewById(R.id.randomComment);
        RandomCommentViewModel randomCommentViewModel = ViewModelProviders.of(this).get(RandomCommentViewModel.class);
        randomCommentViewModel.getCurrentComment().observe(this, comment -> randomComment.setText(comment.getText()));


        // --WEATHER--
        List<WeatherForecastView> dailyForecast = Arrays.asList(
                findViewById(R.id.weatherForecastView1),
                findViewById(R.id.weatherForecastView2),
                findViewById(R.id.weatherForecastView3),
                findViewById(R.id.weatherForecastView4),
                findViewById(R.id.weatherForecastView5)
        );

        WeatherInfoViewHandler weatherInfoViewHandler = new WeatherInfoViewHandler(findViewById(R.id.weatherInfoView));

        List<ViewHandler> viewHandlers = Arrays.asList(
                weatherInfoViewHandler,
                new ForecastGraphHandler(findViewById(R.id.forecastGraph)),
                new WeatherForecastViewHandler(dailyForecast)
        );

        WeatherViewModel weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.getWeather().observe(this, weather -> {
            Stream.of(viewHandlers).forEach(handler -> handler.updateData(weather));
            randomCommentViewModel.putCurrentWeather(weather);
        });
        weatherViewModel.getWeatherSummary().observe(this, weatherInfoViewHandler::updateWeatherComment);


        // probably some bus info handling? todo


        // init web server
//        WebServer webServer = new WebServer(this);
//        try {
//            webServer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        TextView ipAddress = findViewById(R.id.ipAddress);
//        ipAddress.setText(webServer.getIpAddress());

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
