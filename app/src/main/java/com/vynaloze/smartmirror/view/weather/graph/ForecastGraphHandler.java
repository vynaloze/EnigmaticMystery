package com.vynaloze.smartmirror.view.weather.graph;

import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.vynaloze.smartmirror.R;
import com.vynaloze.smartmirror.util.ApplicationContextProvider;

public class ForecastGraphHandler {                             // TODO SHOW ONLY BIGGEST VALUE SO IT NOT OVERLAPS
    private static final String TAG = "ForecastGraphHandler";
    private BarChart graph;
    private BarData barData;
    private int whiteColor;

    public ForecastGraphHandler(BarChart graph) {
        this.graph = graph;
        this.barData = new BarData();
        this.whiteColor = ContextCompat.getColor(ApplicationContextProvider.getContext(), R.color.white);
        initGraph();
        initAxes();
    }

    public void updateData(BarDataSet dataSet) {
        dataSet.setValueFormatter(new PeriodicPercentValueFormatter());
        dataSet.setValueTextColor(whiteColor);
        dataSet.setValueTextSize(14f);
        dataSet.setColor(whiteColor);

        removeData();
        barData.addDataSet(dataSet);
        graph.notifyDataSetChanged();
        graph.invalidate();
        Log.d(TAG, "Updated graph series");
    }

    private void initGraph() {
        graph.setData(barData);
        graph.setFitBars(true);
        graph.setDrawValueAboveBar(true);
        graph.getLegend().setEnabled(false);
        graph.getDescription().setEnabled(false);
    }

    private void initAxes() {
        XAxis xAxis = graph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(whiteColor);
        xAxis.setTextSize(14f);
        xAxis.setValueFormatter(new DayAxisValueFormatter());

        YAxis yAxis = graph.getAxisLeft();
        yAxis.setAxisMaximum(1f);
        yAxis.setAxisMinimum(0);
        yAxis.setEnabled(false);
        yAxis = graph.getAxisRight();
        yAxis.setEnabled(false);
    }

    private void removeData() {
        for (int i = 0; i < barData.getDataSetCount(); i++) {
            barData.removeDataSet(0);
        }
    }
}
