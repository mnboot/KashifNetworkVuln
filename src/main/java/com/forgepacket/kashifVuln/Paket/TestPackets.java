package com.forgepacket.kashifVuln.Paket;

public class TestPackets {

    static void main(String[] args) {

        String filePath = "C:\\Users\\Mnboot\\Downloads\\try_me.pcap";

        try {

            System.out.println("Reading PCAP...");

            PcapFileReader reader = new PcapFileReader(filePath);

            byte[] packet;
            int id = 1;

            while ((packet = reader.nextPacket()) != null) {

                ParsedPacket p = PacketParser.parse(packet, id++);

                printPacket(p);
            }

            reader.close();

            System.out.println("DONE ✅");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printPacket(ParsedPacket p) {

        System.out.println("-------------------------");
        System.out.println("ID: " + p.id);
        System.out.println("Protocol: " + p.protocol);
        System.out.println("Src: " + p.src);
        System.out.println("Dst: " + p.dst);
        System.out.println("Length: " + p.length);
    }
}