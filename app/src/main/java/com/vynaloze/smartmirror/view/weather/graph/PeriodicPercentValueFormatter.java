package com.vynaloze.smartmirror.view.weather.graph;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class PeriodicPercentValueFormatter implements IValueFormatter {
    private DecimalFormat decimalFormat = new DecimalFormat("##%");

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        if (entry.getX() % 4 == 0) {
            return decimalFormat.format(value);
        } else {
            return "";
        }
    }
}
