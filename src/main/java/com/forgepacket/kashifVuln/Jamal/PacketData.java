/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.forgepacket.kashifVuln.Jamal;

/**
 *
 * @author Toxin
 */

/**
 * Represents a single packet entry displayed in the GUI table.
 * Contains metadata and raw packet data.
 */
public class PacketData {

    private final int id;
    private final long timestamp;
    private final String protocol;
    private final int length;
    private final byte[] rawData;
    private String sourceIP;
    private final String destinationIP;

    /**
     * Constructs a PacketData object.
     *
     * @param id            packet ID
     * @param timestamp     packet timestamp
     * @param protocol      protocol type (e.g., TCP, UDP)
     * @param sourceIP      source IP address
     * @param destinationIP destination IP address
     * @param length        packet length
     * @param rawData       raw byte data of packet
     */
    public PacketData(int id, long timestamp, String protocol,
                      String sourceIP, String destinationIP,
                      int length, byte[] rawData) {

        this.id = id;
        this.timestamp = timestamp;
        this.protocol = protocol;
        this.sourceIP = sourceIP;
        this.destinationIP = destinationIP;
        this.length = length;
        this.rawData = rawData;
    }

    /**
     * @return packet ID
     */
    public int getId() {
        return id;
    }

    /**
     * @return packet timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return protocol name
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @return source IP
     */
    public String getSourceIP() {
        return sourceIP;
    }

    /**
     * Updates source IP address.
     *
     * @param sourceIP new source IP
     */
    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    /**
     * @return destination IP
     */
    public String getDestinationIP() {
        return destinationIP;
    }

    /**
     * @return packet length
     */
    public int getLength() {
        return length;
    }

    /**
     * @return raw packet data
     */
    public byte[] getRawData() {
        return rawData;
    }
}
