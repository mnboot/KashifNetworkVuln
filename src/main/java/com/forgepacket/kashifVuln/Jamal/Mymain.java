/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.forgepacket.kashifVuln.Jamal;

/**
 *
 * @author Toxin
 */


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mymain {

    static void main(String[] args) {
        List<PacketData> packets = new ArrayList<>();


        // ⚠️ This is FAKE data (just for testing pipeline)
        packets.add(new PacketData(
                1,
                System.currentTimeMillis(),
                "TCP",
                "1.1.1.1",
                "2.2.2.2",
                4,
                new byte[]{0x45, 0x00, 0x00, 0x28, 0x00, 0x00, 0x40, 0x00}
        ));

        PcapController controller = new PcapController();
        controller.setPackets(packets);

        File file = new File("C:\\Users\\u\\Desktop\\output.pcap");

        controller.export(new File("C:\\Users\\u\\Desktop\\output.pcap"));

        if (file.exists() && file.length() > 0) {
            System.out.println("Export successful, file size: " + file.length());
        } else {
            System.out.println("Export failed!");
        }

        System.out.println("Export done.");
    }
}
