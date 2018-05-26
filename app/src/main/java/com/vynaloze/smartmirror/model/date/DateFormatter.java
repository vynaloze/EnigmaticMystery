package com.vynaloze.smartmirror.model.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateFormatter {
    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("EEEE, d MMM yyyy", Locale.getDefault());
        return format.format(calendar.getTime());
    }
}
