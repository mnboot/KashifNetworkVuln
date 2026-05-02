/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.forgepacket.kashifVuln.Paket;

import java.nio.ByteBuffer;
/**
 *
 * @author alsaf
 */


/**
 * Represents a TCP (Transmission Control Protocol) segment.
 *
 * <p>This class models the structure of a TCP packet including ports,
 * sequence numbers, flags, and payload. It provides functionality to
 * serialize the segment into a byte array suitable for network transmission
 * or further encapsulation inside an IP packet.</p>
 *
 * <p>TCP is a connection-oriented protocol that ensures reliable data
 * transmission between hosts. This implementation includes basic header
 * fields and common flags such as SYN, ACK, FIN, and RST.</p>
 *
 * @author alsaf
 */
public class TCPSegment {

    private int sourcePort;
    private int destinationPort;
    private int sequenceNumber = 0;
    private int acknowledgmentNumber = 0;

    private boolean SYN, ACK, FIN, RST;

    private byte[] payload = new byte[0];

    /**
     * Sets the source port number.
     *
     * @param sourcePort the port number of the sender (0–65535)
     */
    public void setSourcePort(int sourcePort) {
        this.sourcePort = sourcePort;
    }

    /**
     * Sets the destination port number.
     *
     * @param destinationPort the port number of the receiver (0–65535)
     */
    public void setDestinationPort(int destinationPort) {
        this.destinationPort = destinationPort;
    }

    /**
     * Sets the TCP sequence number.
     *
     * @param sequenceNumber the sequence number used for ordering packets
     */
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * Sets the acknowledgment number.
     *
     * @param acknowledgmentNumber the acknowledgment number confirming received data
     */
    public void setAcknowledgmentNumber(int acknowledgmentNumber) {
        this.acknowledgmentNumber = acknowledgmentNumber;
    }

    /**
     * Sets the SYN flag.
     *
     * <p>SYN is used to initiate a TCP connection.</p>
     *
     * @param SYN true to enable SYN flag
     */
    public void setSYN(boolean SYN) {
        this.SYN = SYN;
    }

    /**
     * Sets the ACK flag.
     *
     * <p>ACK indicates acknowledgment of received data.</p>
     *
     * @param ACK true to enable ACK flag
     */
    public void setACK(boolean ACK) {
        this.ACK = ACK;
    }

    /**
     * Sets the FIN flag.
     *
     * <p>FIN is used to gracefully close a TCP connection.</p>
     *
     * @param FIN true to enable FIN flag
     */
    public void setFIN(boolean FIN) {
        this.FIN = FIN;
    }

    /**
     * Sets the RST flag.
     *
     * <p>RST is used to immediately terminate a connection.</p>
     *
     * @param RST true to enable RST flag
     */
    public void setRST(boolean RST) {
        this.RST = RST;
    }

    /**
     * Sets the payload (data) of the TCP segment.
     *
     * @param payload byte array containing application data
     */
    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    /**
     * Builds the TCP flags byte based on enabled flags.
     *
     * @return byte representing TCP flags
     */
    private byte buildFlags() {
        int flags = 0;

        if (FIN) flags |= 0x01;
        if (SYN) flags |= 0x02;
        if (RST) flags |= 0x04;
        if (ACK) flags |= 0x10;

        return (byte) flags;
    }

    /**
     * Serializes the TCP segment into a byte array.
     *
     * <p>The resulting array contains a TCP header followed by payload data.
     * The header is 20 bytes long (no options included).</p>
     *
     * @return byte array representing the TCP segment
     */
    public byte[] serialize() {

        int totalLength = 20 + payload.length;

        ByteBuffer buffer = ByteBuffer.allocate(totalLength);

        // Source and destination ports
        buffer.putShort((short) sourcePort);
        buffer.putShort((short) destinationPort);

        // Sequence and acknowledgment numbers
        buffer.putInt(sequenceNumber);
        buffer.putInt(acknowledgmentNumber);

        // Data offset (header length = 5 × 4 = 20 bytes)
        buffer.put((byte) (5 << 4));

        // Flags
        buffer.put(buildFlags());

        // Window size
        buffer.putShort((short) 65535);

        // Checksum (not implemented yet)
        buffer.putShort((short) 0);

        // Urgent pointer
        buffer.putShort((short) 0);

        // Payload
        buffer.put(payload);

        return buffer.array();
    }

    /**
     * Returns a readable representation of the TCP segment.
     *
     * @return string describing TCP segment fields
     */
    @Override
    public String toString() {
        return "TCPSegment{" +
                "srcPort=" + sourcePort +
                ", dstPort=" + destinationPort +
                ", SYN=" + SYN +
                ", ACK=" + ACK +
                ", FIN=" + FIN +
                ", RST=" + RST +
                '}';
    }
}