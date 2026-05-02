package com.forgepacket.kashifVuln.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

import java.io.IOException;

public class MainController {

    public Tab tapCapture;
    public Tab tapCraft;
    public Tab tapScanner;
    public Tab tapAttack;
    public Tab tapAnalyzer;

    //==================
    // modifiable utility
    //==================
    private void addRoutes() {
        setTabView(tapCapture, "CaptureController.fxml");
        setTabView(tapCraft, "CraftController.fxml");
        setTabView(tapScanner, "ScannerController.fxml");
    }


    //===================
    // functional
    //===================
    public void initialize() {
        // Routes
        addRoutes();
    }


    //===================
    // utility
    //===================
    private void setTabView(Tab tab, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent content = loader.load();
            tab.setContent(content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}