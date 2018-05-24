package com.vynaloze.smartmirror.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CalendarViewModel extends ViewModel {
    private static final String TAG = "CalendarViewModel";
    private static final int PERIOD = 15;

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
        executor.scheduleAtFixedRate(new DateUpdater(), 0, PERIOD, TimeUnit.SECONDS);
    }

    private class DateUpdater implements Runnable {
        @Override
        public void run() {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("EEEE, d MMM yyyy", Locale.getDefault());
            String formattedDate = format.format(calendar.getTime());
            date.postValue(formattedDate);
            Log.d(TAG, "Updated date to " + formattedDate);
        }
    }
}
