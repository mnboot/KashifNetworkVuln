package com.forgepacket.kashifVuln.Jamal.etc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;

public class LabProject extends JFrame {

    private final String DATA_FILE = "NewtworkSavefile.txt";

    ArrayList<Threat> threats = new ArrayList<>();
    int nextId = 1;

    DefaultTableModel model;
    JTable table;

    public LabProject() {

        setTitle("Smart Network Monitor");
        setSize(750, 420);
        setLocationRelativeTo(null);

        // Save on close
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                saveData();
                System.exit(0);
            }
        });

        String[] cols = {"ID", "IP", "Type", "Severity", "Status"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        JButton resolveBtn = new JButton("Resolve");
        JButton removeBtn = new JButton("Remove");

        JPanel panel = new JPanel();
        panel.add(resolveBtn);
        panel.add(removeBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        resolveBtn.addActionListener(e -> resolveThreat());
        removeBtn.addActionListener(e -> removeThreat());

        loadData();
        refreshTable();

        setVisible(true);

        startScan();
    }

    static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LabProject());
    }

    void startScan() {
        new Thread(() -> {
            try {
                String subnet = "192.168.0."; // change if needed

                java.util.concurrent.ExecutorService executor =
                        java.util.concurrent.Executors.newFixedThreadPool(50);

                for (int i = 1; i < 255; i++) {
                    final String ip = subnet + i;

                    executor.execute(() -> {
                        try {
                            InetAddress addr = InetAddress.getByName(ip);

                            if (addr.isReachable(150)) {
                                String name = addr.getHostName();
                                String type = ip.endsWith(".1")
                                        ? "Router"
                                        : "Device (" + name + ")";
                                String severity = ip.endsWith(".1")
                                        ? "MEDIUM"
                                        : "LOW";
                                synchronized (threats) {
                                    if (!exists(ip)) {
                                        threats.add(new Threat(nextId++, ip, type, severity));

                                        SwingUtilities.invokeLater(() -> refreshTable());
                                    }
                                }
                            }
                        } catch (Exception ignored) {
                        }
                    });
                }
                executor.shutdown();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Threat t : threats) {
                writer.println(t.getId() + "," + t.getSourceIP() + "," + t.getType() + "," + t.getSeverity() + "," + t.getStatus()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");

                Threat t = new Threat(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);

                t.setStatus(parts[4]);

                threats.add(t);

                if (t.getId() >= nextId) {
                    nextId = t.getId() + 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean exists(String ip) {
        for (Threat t : threats) {
            if (t.getSourceIP().equals(ip)) return true;
        }
        return false;
    }

    void resolveThreat() {
        String input = JOptionPane.showInputDialog(this, "Enter ID:");

        if (input == null || input.trim().isEmpty()) return;
        try {
            int id = Integer.parseInt(input);

            for (Threat t : threats) {
                if (t.getId() == id) {
                    t.setStatus("RESOLVED");
                }
            }
            saveData();
            refreshTable();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid ID");
        }
    }

    void removeThreat() {
        String input = JOptionPane.showInputDialog(this, "Enter ID:");

        if (input == null || input.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(input);

            threats.removeIf(t -> t.getId() == id);
            saveData();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid ID");
        }
    }

    void refreshTable() {
        model.setRowCount(0);

        for (Threat t : threats) {
            model.addRow(new Object[]{t.getId(), t.getSourceIP(), t.getType(), t.getSeverity(), t.getStatus()});
        }
    }
}