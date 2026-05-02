package com.forgepacket.kashifVuln.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CraftController {
    public ComboBox comboProto;
    public TextField txtSource;
    public TextField txtDest;


    @FXML
    public void initialize() {
        System.out.println("Dashboard Controller initialized");
    }

}
