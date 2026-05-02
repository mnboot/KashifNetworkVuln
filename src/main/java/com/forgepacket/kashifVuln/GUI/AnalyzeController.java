package com.forgepacket.kashifVuln.GUI;

import com.forgepacket.kashifVuln.Paket.ParsedPacket;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class AnalyzeController {
    private final XYChart.Series<String, Number> series = new XYChart.Series<>();
    public Label lblStatus;
    public BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        startThreading();
        barChart.getData().add(series);
    }

    private void startThreading() {
        new Thread(() -> {


            while (true) {
                try {
                    Thread.sleep(1000);
                    Platform.runLater(() -> {
                        if (Shared.pcapFile.isEmpty()) {
                            lblStatus.setText("No pcap file found!");
                            lblStatus.setTextFill(Color.RED);
                            series.getData().clear();
                        } else {
                            lblStatus.setText("Pcap file found!");
                            lblStatus.setTextFill(Color.GREEN);
                            updateChart();
                        }
                    });

                } catch (InterruptedException _) {
                }
            }


        }, "Update Chart").start();
    }

    private void updateChart() {
        Map<String, Integer> counts = new HashMap<>();
        for (ParsedPacket packet : Shared.pcapFile) {
            String protocol = packet.getProtocol();
            counts.put(protocol,
                    counts.getOrDefault(protocol, 0) + 1);
        }
        series.getData().clear();

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            System.out.println(entry.getValue());
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }


    }
}
