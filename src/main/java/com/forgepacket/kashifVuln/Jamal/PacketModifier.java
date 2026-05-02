/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.forgepacket.kashifVuln.Jamal;

/**
 *
 * @author Toxin
 */


import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.factory.PacketFactories;
import org.pcap4j.packet.namednumber.DataLinkType;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.List;

/**
 * Provides operations for modifying packet data at both model and raw byte level.
 */
public class PacketModifier {

    /**
     * Replaces source IP in all packets (real byte-level modification).
     *
     * @param packets list of packets
     * @param oldIP   IP to replace
     * @param newIP   new IP value
     */
    public void replaceSourceIP(List<PacketData> packets, String oldIP, String newIP) {
        for (int i = 0; i < packets.size(); i++) {
            PacketData packet = packets.get(i);

            if (!packet.getSourceIP().equals(oldIP)) {
                continue;
            }

            PacketData modified = modifySourceIP(packet, newIP);

            packets.set(i, modified);
        }
    }

    /**
     * Removes packets matching a source IP.
     *
     * @param packets list of packets
     * @param ip      IP to remove
     */
    public void removePacketsBySourceIP(List<PacketData> packets, String ip) {
        packets.removeIf(packet -> packet.getSourceIP().equals(ip));
    }

    /**
     * Performs actual packet byte modification using Pcap4J.
     *
     * @param data  original packet
     * @param newIP new source IP
     * @return modified packet
     */
    private PacketData modifySourceIP(PacketData data, String newIP) {
        try {
            Packet packet = PacketFactories
                    .getFactory(Packet.class, DataLinkType.class)
                    .newInstance(data.getRawData(), 0, data.getRawData().length);

            IpV4Packet ipPacket = packet.get(IpV4Packet.class);

            if (ipPacket == null) {
                // fallback for non-parsable packets (e.g., tests)
                data.setSourceIP(newIP);
                return data;
            }

            // Clone builder
            IpV4Packet.Builder ipBuilder = ipPacket.getBuilder();

            // Set new source IP
            ipBuilder.srcAddr((Inet4Address) InetAddress.getByName(newIP));

            // Let Pcap4J fix checksums automatically
            ipBuilder.correctChecksumAtBuild(true);
            ipBuilder.correctLengthAtBuild(true);

            Packet newPacket = ipBuilder.build();

            return new PacketData(
                    data.getId(),
                    data.getTimestamp(),
                    data.getProtocol(),
                    newIP,
                    data.getDestinationIP(),
                    newPacket.length(),
                    newPacket.getRawData()
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to modify packet source IP", e);
        }
    }
}