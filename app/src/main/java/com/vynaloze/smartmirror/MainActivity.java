package com.vynaloze.smartmirror;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.vynaloze.smartmirror.viewmodel.CalendarViewModel;

public class MainActivity extends FragmentActivity {

    TextView calendarTextView; //todo ui to classes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarTextView = findViewById(R.id.calendarTextView);

        CalendarViewModel calendarViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        calendarViewModel.getDate().observe(this, date -> calendarTextView.setText(date));


    }
}
