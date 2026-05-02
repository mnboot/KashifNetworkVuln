package com.forgepacket.kashifVuln.Paket;

import java.nio.ByteBuffer;


/**
 * Represents an IPv4 packet.
 * <p>
 * This class handles core IPv4 header fields
 *
 */
public class IPPacket {

    private String sourceIP;
    private String destinationIP;
    private int ttl = 64;
    private int protocol; // TCP = 6, UDP = 17
    private byte[] payload;


    public IPPacket() {
        this.payload = new byte[0];
    }

    public static void testBasic() {
        IPPacket ip = new IPPacket();
        ip.setSourceIP("192.168.1.1");
        ip.setDestinationIP("8.8.8.8");
        ip.setTTL(64);
        ip.setProtocol(6);

        byte[] data = ip.serialize();

        System.out.println("Length: " + data.length);
    }

    public static void testPayload() {
        IPPacket ip = new IPPacket();
        ip.setSourceIP("192.168.1.1");
        ip.setDestinationIP("8.8.8.8");
        ip.setProtocol(6);

        ip.setPayload("HELLO".getBytes());

        byte[] data = ip.serialize();

        System.out.println("Expected: 25");
        System.out.println("Actual: " + data.length);
    }

    public static void testFields() {
        IPPacket ip = new IPPacket();
        ip.setSourceIP("10.0.0.1");
        ip.setDestinationIP("20.0.0.1");
        ip.setTTL(128);
        ip.setProtocol(17); // UDP

        byte[] data = ip.serialize();

        int ttl = data[8] & 0xFF;
        int protocol = data[9] & 0xFF;

        System.out.println("TTL (Expected 128): " + ttl);
        System.out.println("Protocol (Expected 17): " + protocol);
    }

    public static void testHexDump() {
        IPPacket ip = new IPPacket();
        ip.setSourceIP("1.1.1.1");
        ip.setDestinationIP("2.2.2.2");
        ip.setProtocol(6);

        byte[] data = ip.serialize();

        for (int i = 0; i < data.length; i++) {
            System.out.printf("%02X ", data[i]);

            if ((i + 1) % 16 == 0) System.out.println();
        }
    }

    /**
     * @return source IP address
     */
    public String getSourceIP() {
        return sourceIP;
    }

    /**
     * Sets the source IP address.
     *
     * @param sourceIP IP address in dotted format (e.g., "192.168.1.1")
     */
    public void setSourceIP(String sourceIP) {
        this.sourceIP = sourceIP;
    }

    /**
     * @return destination IP address
     */
    public String getDestinationIP() {
        return destinationIP;
    }

    /**
     * Sets the destination IP address.
     *
     * @param destinationIP IP address in dotted format
     */
    public void setDestinationIP(String destinationIP) {
        this.destinationIP = destinationIP;
    }

    /**
     * @return TTL value
     */
    public int getTTL() {
        return ttl;
    }

    /**
     * Sets TTL (Time To Live).
     *
     * @param ttl number of hops
     */
    public void setTTL(int ttl) {
        this.ttl = ttl;
    }

    /**
     * @return protocol value
     */
    public int getProtocol() {
        return protocol;
    }

    /**
     * Sets protocol number.
     *
     * @param protocol TCP = 6, UDP = 17
     */
    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    /**
     * @return payload data
     */
    public byte[] getPayload() {
        return payload;
    }

    /**
     * Sets payload (data carried by IP packet).
     *
     * @param payload byte array payload
     */
    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    /**
     * Converts IP string to byte array.
     */
    private byte[] ipToBytes(String ip) {
        String[] parts = ip.split("\\.");
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) Integer.parseInt(parts[i]);
        }
        return bytes;
    }

    /**
     * Serializes the IP packet into raw bytes.
     *
     * @return byte array representing the packet
     */
    public byte[] serialize() {

        int headerLength = 20;
        int totalLength = headerLength + payload.length;

        ByteBuffer buffer = ByteBuffer.allocate(totalLength);

        // Version (4 bits) + IHL (4 bits)
        buffer.put((byte) 0x45);

        // DSCP + ECN
        buffer.put((byte) 0);

        // Total Length
        buffer.putShort((short) totalLength);

        // Identification
        buffer.putShort((short) 0);

        // Flags + Fragment Offset
        buffer.putShort((short) 0);

        // TTL
        buffer.put((byte) ttl);

        // Protocol
        buffer.put((byte) protocol);

        // Checksum placeholder
        buffer.putShort((short) 0);

        // Source IP
        buffer.put(ipToBytes(sourceIP));

        // Destination IP
        buffer.put(ipToBytes(destinationIP));

        // Payload
        buffer.put(payload);

        byte[] packetBytes = buffer.array();

        // Compute checksum
        short checksum = computeChecksum(packetBytes);
        packetBytes[10] = (byte) (checksum >> 8);
        packetBytes[11] = (byte) (checksum);

        return packetBytes;
    }

    /**
     * Computes IPv4 header checksum.
     *
     * @param data byte array of packet
     * @return checksum value
     */
    private short computeChecksum(byte[] data) {
        int sum = 0;

        for (int i = 0; i < 20; i += 2) {
            int word = ((data[i] << 8) & 0xFF00) | (data[i + 1] & 0xFF);
            sum += word;
        }

        while ((sum >> 16) != 0) {
            sum = (sum & 0xFFFF) + (sum >> 16);
        }

        return (short) ~sum;
    }

    /**
     * Returns readable representation of packet.
     *
     * @return
     */

    @Override
    public String toString() {
        return "IPPacket{" +
                "src=" + sourceIP +
                ", dst=" + destinationIP +
                ", ttl=" + ttl +
                ", protocol=" + protocol +
                ", payloadSize=" + payload.length +
                '}';
    }
}


    
    
    
    
