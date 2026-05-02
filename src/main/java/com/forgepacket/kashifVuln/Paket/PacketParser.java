package com.forgepacket.kashifVuln.Paket;

import java.nio.ByteBuffer;

/**
 * Parses raw packet bytes into structured data.
 */
public class PacketParser {

    /**
     * Parses a raw packet.
     *
     * @param data raw packet bytes
     * @param id   packet ID
     * @return parsed packet
     */
    public static ParsedPacket parse(byte[] data, int id) {

        String protocol = "UNK";
        String src = "-";
        String dst = "-";
        String info = "";
        int length = data.length;
        String time = java.time.LocalTime.now().toString();

        if (data.length < 34) {
            return new ParsedPacket(id, time, protocol, src, dst, length, info);
        }

        int etherType = readShort(data, 12);

        // IPv4
        if (etherType == 0x0800) {

            int ipStart = 14;
            int proto = data[ipStart + 9] & 0xFF;

            src = parseIP(data, ipStart + 12);
            dst = parseIP(data, ipStart + 16);

            if (proto == 6) {
                protocol = "TCP";
                info = parseTCP(data, ipStart);
            } else if (proto == 17) {
                protocol = "UDP";
                info = parseUDP(data, ipStart);
            } else {
                protocol = "IP";
            }
        }

        // ARP
        else if (etherType == 0x0806) {
            protocol = "ARP";
            info = "ARP";
        }

        return new ParsedPacket(id, time, protocol, src, dst, length, info);
    }

    /**
     * Parses TCP segment.
     */
    private static String parseTCP(byte[] data, int ipStart) {

        int ipHeaderLen = (data[ipStart] & 0x0F) * 4;
        int tcpStart = ipStart + ipHeaderLen;

        int srcPort = readShort(data, tcpStart);
        int dstPort = readShort(data, tcpStart + 2);

        long seq = readInt(data, tcpStart + 4);
        long ack = readInt(data, tcpStart + 8);

        int flags = data[tcpStart + 13] & 0xFF;

        String flagStr = "";
        if ((flags & 0x02) != 0) flagStr += "SYN ";
        if ((flags & 0x10) != 0) flagStr += "ACK ";
        if ((flags & 0x01) != 0) flagStr += "FIN ";

        String app = "";

        // HTTP detection (port 80 only)
        if (srcPort == 80 || dstPort == 80) {
            app = parseHTTP(data, tcpStart);
        }

        return (app.isEmpty() ? "" : app + " ") +
                "Seq=" + seq +
                " Ack=" + ack +
                " [" + flagStr.trim() + "] " +
                srcPort + " → " + dstPort;
    }

    /**
     * Parses UDP datagram.
     */
    private static String parseUDP(byte[] data, int ipStart) {

        int ipHeaderLen = (data[ipStart] & 0x0F) * 4;
        int udpStart = ipStart + ipHeaderLen;

        int srcPort = readShort(data, udpStart);
        int dstPort = readShort(data, udpStart + 2);

        // DNS detection
        if (srcPort == 53 || dstPort == 53) {
            return parseDNS(data, udpStart + 8);
        }

        return srcPort + " → " + dstPort;
    }

    /**
     * Parses HTTP payload (GET/POST/response).
     */
    private static String parseHTTP(byte[] data, int tcpStart) {

        try {
            int offset = ((data[tcpStart + 12] >> 4) & 0xF) * 4;
            int payloadStart = tcpStart + offset;

            int len = data.length - payloadStart;
            if (len <= 0) return "";

            String payload = new String(data, payloadStart, Math.min(len, 200));
            String line = payload.split("\r\n")[0];

            if (line.startsWith("GET") ||
                    line.startsWith("POST") ||
                    line.startsWith("HTTP/")) {
                return line;
            }

        } catch (Exception ignored) {
        }

        return "";
    }

    /**
     * Parses DNS query name.
     */
    private static String parseDNS(byte[] data, int start) {

        try {
            int pos = start + 12;
            StringBuilder name = new StringBuilder();

            while (data[pos] != 0) {
                int len = data[pos++];
                for (int i = 0; i < len; i++) {
                    name.append((char) data[pos++]);
                }
                name.append(".");
            }

            return "DNS " + name;

        } catch (Exception e) {
            return "DNS";
        }
    }

    /**
     * Reads IPv4 address.
     */
    private static String parseIP(byte[] data, int start) {
        return (data[start] & 0xFF) + "." +
                (data[start + 1] & 0xFF) + "." +
                (data[start + 2] & 0xFF) + "." +
                (data[start + 3] & 0xFF);
    }

    /**
     * Reads 2 bytes as unsigned short.
     */
    private static int readShort(byte[] data, int pos) {
        return ((data[pos] & 0xFF) << 8) | (data[pos + 1] & 0xFF);
    }

    /**
     * Reads 4 bytes as unsigned int.
     */
    private static long readInt(byte[] data, int pos) {
        return ByteBuffer.wrap(data, pos, 4).getInt() & 0xFFFFFFFFL;
    }
}
