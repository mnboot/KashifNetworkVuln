package com.forgepacket.kashifVuln.Paket;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * PcapFileReader
 * <p>
 * Pure Java PCAP reader (no external/native libraries).
 * Reads classic .pcap files and returns raw packet bytes.
 */
public class PcapFileReader {

    private final InputStream in;
    private boolean littleEndian = true;

    /**
     * Constructor
     */
    public PcapFileReader(String filePath) throws IOException {
        in = new FileInputStream(filePath);
        readGlobalHeader();
    }

    /**
     * Reads global PCAP header (24 bytes)
     */
    private void readGlobalHeader() throws IOException {

        byte[] header = new byte[24];
        readFully(header);

        int magic = ByteBuffer.wrap(header, 0, 4)
                .order(ByteOrder.BIG_ENDIAN)
                .getInt();

        if (magic == 0xa1b2c3d4) {
            littleEndian = false;
        } else if (magic == 0xd4c3b2a1) {
            littleEndian = true;
        } else {
            throw new IOException("Invalid PCAP file format");
        }
    }

    /**
     * Reads next packet from file
     */
    public byte[] nextPacket() throws IOException {

        byte[] pktHeader = new byte[16];

        int read = in.read(pktHeader);
        if (read < 16) return null; // EOF

        ByteBuffer bb = ByteBuffer.wrap(pktHeader);
        bb.order(littleEndian ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);

        bb.getInt(); // ts_sec
        bb.getInt(); // ts_usec
        int inclLen = bb.getInt(); // captured length
        bb.getInt(); // original length

        byte[] data = new byte[inclLen];
        readFully(data);

        return data;
    }

    /**
     * Utility method to read full byte array
     */
    private void readFully(byte[] buffer) throws IOException {
        int offset = 0;
        while (offset < buffer.length) {
            int read = in.read(buffer, offset, buffer.length - offset);
            if (read == -1) throw new EOFException();
            offset += read;
        }
    }

    /**
     * Close file
     */
    public void close() throws IOException {
        in.close();
    }
}