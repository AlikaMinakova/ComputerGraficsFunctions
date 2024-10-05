package com.cgvsu.protocurvefxapp;

import javafx.scene.chart.XYChart;

import java.util.function.Function;

public class MyGraph {

    private XYChart<Double, Double> graph;
    private double range;

    public MyGraph(final XYChart<Double, Double> graph, final double range) {
        this.graph = graph;
        this.range = range;
    }

    public void plotLine(String str) {
        final XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
        //0.0.1
        for (double x = -range; x <= range; x = x + 0.03) {
            plotPoint(x, ParserFunction.parser(str, x), series);
        }
        clear();
        graph.getData().add(series);
    }

    private void plotPoint(final double x, final double y,
                           final XYChart.Series<Double, Double> series) {
        series.getData().add(new XYChart.Data<Double, Double>(x, y));
    }

    public void clear() {
        graph.getData().clear();
    }
}
