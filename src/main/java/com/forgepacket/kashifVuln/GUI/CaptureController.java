package com.forgepacket.kashifVuln.GUI;

import com.forgepacket.kashifVuln.Paket.PacketParser;
import com.forgepacket.kashifVuln.Paket.ParsedPacket;
import com.forgepacket.kashifVuln.Paket.PcapFileReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class CaptureController {


    public TableView<ParsedPacket> tableCapture;
    public TableColumn<ParsedPacket, Integer> colId;
    public TableColumn<ParsedPacket, String> colProtocol;
    public TableColumn<ParsedPacket, String> colSrc;
    public TableColumn<ParsedPacket, String> colDst;
    public TableColumn<ParsedPacket, String> colLength;
    public TextField txtFileName;
    public TableColumn<ParsedPacket, String> colInfo;


    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProtocol.setCellValueFactory(new PropertyValueFactory<>("protocol"));
        colSrc.setCellValueFactory(new PropertyValueFactory<>("src"));
        colDst.setCellValueFactory(new PropertyValueFactory<>("dst"));
        colLength.setCellValueFactory(new PropertyValueFactory<>("length"));
        colInfo.setCellValueFactory(new PropertyValueFactory<>("info"));
    }

    private void updateTable() throws IOException {
        tableCapture.getItems().clear();


        for (ParsedPacket p : Shared.pcapFile) {
            tableCapture.getItems().add(p);

        }


    }

    private void storePacket(PcapFileReader pcapReader) throws IOException {
        byte[] packet;
        int id = 1;

        while ((packet = pcapReader.nextPacket()) != null) {

            Shared.pcapFile.add(PacketParser.parse(packet, id++));

        }
    }


    public void ocSelectFile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open PCAP File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PCAP Files", "*.pcap")
        );

        Stage stage = (Stage)
                ((Node) actionEvent.getSource())
                        .getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            storePacket(new PcapFileReader(file.getAbsolutePath()));
            txtFileName.setText(file.getName());
            updateTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No file selected");
            alert.showAndWait();
        }

    }

    public void ocCloseFile(ActionEvent actionEvent) {
        tableCapture.getItems().clear();
        txtFileName.setText("UNLOADED");

        Shared.pcapFile.clear();


    }


}
