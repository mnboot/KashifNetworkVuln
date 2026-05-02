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
import java.util.List;

/**
 * Central controller that coordinates packet modification and export operations.
 */
public class PcapController {

    private final PacketModifier modifier;
    private final PcapExporter exporter;
    private final IPRegistry registry;

    private List<PacketData> packets;

    /**
     * Initializes controller components
     */
    public PcapController() {
        this.modifier = new PacketModifier();
        this.exporter = new PcapExporter();
        this.registry = new IPRegistry();
    }

    /**
     * Replaces source IP in packets.
     *
     * @param oldIP old IP
     * @param newIP new IP
     */
    public void replaceSourceIP(String oldIP, String newIP) {
        modifier.replaceSourceIP(packets, oldIP, newIP);
    }

    /**
     * Removes packets by source IP.
     *
     * @param ip IP to remove
     */
    public void removePackets(String ip) {
        modifier.removePacketsBySourceIP(packets, ip);
    }

    /**
     * Assigns a name to an IP.
     *
     * @param ip   IP address
     * @param name display name
     */
    public void registerIP(String ip, String name) {
        registry.addMapping(ip, name);
    }

    /**
     * Resolves an IP to a name.
     *
     * @param ip IP address
     * @return associated name
     */
    public String resolveIP(String ip) {
        return registry.resolve(ip);
    }

    /**
     * Exports packets to a PCAP file.
     *
     * @param file output file
     */
    public void export(File file) {
        exporter.export(packets, file);
    }

    /**
     * Gets current packet list.
     *
     * @return list of packets
     */
    public List<PacketData> getPackets() {
        return packets;
    }

    /**
     * Sets packet list (provided by another module).
     *
     * @param packets list of packets
     */
    public void setPackets(List<PacketData> packets) {
        this.packets = packets;
    }
}
