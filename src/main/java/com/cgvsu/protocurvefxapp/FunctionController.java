package com.cgvsu.protocurvefxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

public class FunctionController implements Initializable {

    @FXML
    public TextField textField;

    @FXML
    private AreaChart<Double, Double> areaGraph;

    private MyGraph areaMathsGraph;

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        areaMathsGraph = new MyGraph(areaGraph, 20);
    }

    private void plotLine(String str) {
        areaMathsGraph.plotLine(str);
    }

    @FXML
    private void create(final ActionEvent event) {
        plotLine(textField.getText());
    }
}
