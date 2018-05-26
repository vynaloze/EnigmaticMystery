package com.vynaloze.smartmirror.view;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class ForecastGraphHandler {

    public static void handle(GraphView graph, BarGraphSeries<DataPoint> series) {
        graph.removeAllSeries();
        graph.addSeries(series);    //todo: all formatting of a graph (epoch->date, size, colors, 24h only?)
        //+ packaging
    }
}
