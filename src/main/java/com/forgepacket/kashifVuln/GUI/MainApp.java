package com.forgepacket.kashifVuln.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("MainApp.fxml")
        );
        Scene scene = new Scene(loader.load(), 640, 480);
        stage.setScene(scene);
        stage.setTitle("Kashif Network Vuln");
        stage.show();
    }
}
