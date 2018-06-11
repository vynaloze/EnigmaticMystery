package com.vynaloze.smartmirror.view.weather.graph;

import android.util.Log;

import com.annimon.stream.Stream;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PeriodicPercentValueFormatter implements IValueFormatter {
    private DecimalFormat decimalFormat = new DecimalFormat("##%");
    private List<Integer> indexesToShow;

    public PeriodicPercentValueFormatter(BarDataSet dataSet) {
        super();
        indexesToShow = new IndexCalculator(dataSet).calculateIndexesToShow();
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        if (indexesToShow.contains((int) entry.getX())) {
            return decimalFormat.format(value);
        } else {
            return "";
        }
    }

    private class IndexCalculator {
        private BarDataSet dataSet;
        private static final String TAG = "PPVF.IndexCalculator";
        private int indexOfMaxValue;

        private IndexCalculator(BarDataSet dataSet) {
            this.dataSet = dataSet;
            indexesToShow = new ArrayList<>();
        }

        private List<Integer> calculateIndexesToShow() {
            putIndexOfMaxValue();
            putIndexesWithSimilarValues();
            removeExcessIndexes();
            return indexesToShow;
        }

        private void putIndexOfMaxValue() {
            BarEntry max = Stream.of(dataSet.getValues()).max((e1, e2) -> Float.compare(e1.getY(), e2.getY())).get();
            indexOfMaxValue = (int) max.getX();
            indexesToShow.add(indexOfMaxValue);
            Log.d(TAG, "Found max value " + max.getY() + " with index " + indexOfMaxValue);
        }

        private void putIndexesWithSimilarValues() {
            final float MAX_DIFFERENCE = 0.05f;
            for (int i = 1; i < dataSet.getValues().size() - 1; i++) {
                float previous = dataSet.getEntryForIndex(i - 1).getY();
                float current = dataSet.getEntryForIndex(i).getY();
                float next = dataSet.getEntryForIndex(i + 1).getY();
                if (current - previous < MAX_DIFFERENCE && next - previous < MAX_DIFFERENCE) {
                    indexesToShow.add(i);
                    Log.d(TAG, "Added index " + i + "; values(previous,current,next): " + previous + current + next);
                }
            }
        }

        private void removeExcessIndexes() {
            AtomicInteger iteration = new AtomicInteger(0);
            while (indexesToShow.size() > dataSet.getValues().size() / 4) {
                indexesToShow = Stream.of(indexesToShow).filter(i -> i % 4 != iteration.get() || i == indexOfMaxValue).toList();
                iteration.addAndGet(1);
                Log.d(TAG, indexesToShow.toString());
            }
        }
    }
}
