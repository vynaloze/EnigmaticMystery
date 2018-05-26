package com.vynaloze.smartmirror.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.vynaloze.smartmirror.model.date.DateFormatter;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CalendarViewModel extends ViewModel {
    private static final String TAG = "CalendarViewModel";

    private MutableLiveData<String> date;

    public LiveData<String> getDate() {
        if (date == null) {
            date = new MutableLiveData<>();
            updateDate();
        }
        return date;
    }

    private void updateDate() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String formattedDate = DateFormatter.getDate();
                if (!formattedDate.equals(date.getValue())) {
                    date.postValue(formattedDate);
                    Log.d(TAG, "Updated date to " + formattedDate);
                }
            }
        }, 0, 15, TimeUnit.SECONDS);
    }
}
