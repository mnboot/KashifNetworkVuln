package com.forgepacket.kashifVuln.Paket;

import java.nio.ByteBuffer;

/**
 * Represents an ARP packet (Ethernet/IPv4).
 */
public class ARPPacket {

    private final short hardwareType = 1;     // Ethernet
    private final short protocolType = (short) 0x0800; // IPv4
    private final byte hardwareSize = 6;      // MAC length
    private final byte protocolSize = 4;      // IP length

    private short opcode;               // 1=request, 2=reply

    private byte[] senderMAC = new byte[6];
    private byte[] senderIP = new byte[4];
    private byte[] targetMAC = new byte[6];
    private byte[] targetIP = new byte[4];

    public void setOpcodeRequest() {
        this.opcode = 1;
    }

    public void setOpcodeReply() {
        this.opcode = 2;
    }

    public void setSenderMAC(String mac) {
        this.senderMAC = macToBytes(mac);
    }

    public void setTargetMAC(String mac) {
        this.targetMAC = macToBytes(mac);
    }

    public void setSenderIP(String ip) {
        this.senderIP = ipToBytes(ip);
    }

    public void setTargetIP(String ip) {
        this.targetIP = ipToBytes(ip);
    }

    public byte[] serialize() {

        ByteBuffer buffer = ByteBuffer.allocate(28);

        buffer.putShort(hardwareType);
        buffer.putShort(protocolType);

        buffer.put(hardwareSize);

        buffer.put(protocolSize);
        buffer.putShort(opcode);

        buffer.put(senderMAC);
        buffer.put(senderIP);
        buffer.put(targetMAC);
        buffer.put(targetIP);

        return buffer.array();
    }

    private byte[] ipToBytes(String ip) {
        String[] parts = ip.split("\\.");   // makes the IP look loke like dis ["192", "168", "1", "1"] 
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) Integer.parseInt(parts[i]);
        }
        return bytes;
    }

    private byte[] macToBytes(String mac) {
        String[] parts = mac.split(":");
        byte[] bytes = new byte[6];
        for (int i = 0; i < 6; i++) {
            bytes[i] = (byte) Integer.parseInt(parts[i], 16);
        }
        return bytes;
    }

    @Override
    public String toString() {
        return "ARPPacket{" + "opcode=" + opcode + '}';
    }
}