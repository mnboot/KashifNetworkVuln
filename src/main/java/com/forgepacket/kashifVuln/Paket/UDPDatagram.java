package com.forgepacket.kashifVuln.Paket;

import java.nio.ByteBuffer;

/**
 * Represents a UDP datagram.
 */
public class UDPDatagram {

    private int sourcePort;
    private int destinationPort;
    private byte[] payload = new byte[0];

    public void setSourcePort(int sourcePort) {
        this.sourcePort = sourcePort;
    }

    public void setDestinationPort(int destinationPort) {
        this.destinationPort = destinationPort;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public byte[] serialize() {

        if (payload == null) {
            payload = new byte[0];
        }

        int totalLength = 8 + payload.length;

        ByteBuffer buffer = ByteBuffer.allocate(totalLength);

        buffer.putShort((short) sourcePort);
        buffer.putShort((short) destinationPort);
        buffer.putShort((short) totalLength);
        buffer.putShort((short) 0); // checksum
        buffer.put(payload);

        return buffer.array();
    }
}