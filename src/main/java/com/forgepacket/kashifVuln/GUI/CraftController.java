package com.forgepacket.kashifVuln.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CraftController {
    public ComboBox comboProto;
    public TextField txtSource;
    public TextField txtDest;
    public TextField txtDestinationPort;
    public TextField txtSourcePort;
    public TextField txtTTL;
    public CheckBox chkSyn;
    public CheckBox chkAck;
    public CheckBox chkFin;
    public CheckBox chkRst;
    public CheckBox chkPsh;
    public CheckBox chkUrg;

    @FXML
    public void initialize() {
        System.out.println("Dashboard Controller initialized");
    }

}
