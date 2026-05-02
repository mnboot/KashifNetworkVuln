/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.forgepacket.kashifVuln.Jamal;

/**
 *
 * @author Toxin
 */

import org.pcap4j.core.PcapDumper;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

import java.io.File;
import java.util.List;

/**
 * Handles exporting modified packets back to a PCAP file.
 */
public class PcapExporter {

    /**
     * Exports packets to a PCAP file.
     *
     * @param packets    list of packets
     * @param outputFile destination file
     */
    public void export(List<PacketData> packets, File outputFile) {
        System.out.println("Exporting " + packets.size() + " packets to " + outputFile.getName());

        try {
            PcapHandle handle = Pcaps.openDead(
                    org.pcap4j.packet.namednumber.DataLinkType.EN10MB,
                    65536
            );

            PcapDumper dumper = handle.dumpOpen(outputFile.getAbsolutePath());

            for (PacketData data : packets) {
                try {
                    Packet packet = org.pcap4j.packet.UnknownPacket.newPacket(
                            data.getRawData(),
                            0,
                            data.getRawData().length
                    );

                    dumper.dump(packet, new java.sql.Timestamp(System.currentTimeMillis()));

                } catch (Exception e) {
                    System.out.println("Skipping invalid packet...");
                }
            }

            dumper.close();
            handle.close();

            System.out.println("Export success!");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Export failed!");
        }
    }
}

