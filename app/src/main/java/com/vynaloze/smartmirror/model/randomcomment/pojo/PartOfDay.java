package com.vynaloze.smartmirror.model.randomcomment.pojo;

import java.util.Calendar;

public enum PartOfDay {
    ALL(0, 24), MORNING(5, 12), AFTERNOON(12, 17), EVENING(17, 23), NIGHT(23, 5);

    private int startTime;
    private int endTime;

    PartOfDay(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static PartOfDay getCurrentPartOfDay() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        for (PartOfDay part : PartOfDay.values()) {
            if (hour < part.endTime && hour >= part.startTime && part != ALL) {
                return part;
            }
        }
        return PartOfDay.ALL;
    }
}
