package com.cgvsu.protocurvefxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainwindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 850);
            stage.setScene(scene);
            stage.setTitle("График функции");
            stage.show();
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}