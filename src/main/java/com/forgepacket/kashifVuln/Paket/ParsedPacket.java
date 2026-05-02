/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.forgepacket.kashifVuln.Paket;

/**
 *
 * @author alsaf
 */

/**
 * Represents a parsed packet for GUI display.
 */
public class ParsedPacket {

    public int id;
    public String time;
    public String protocol;
    public String src;
    public String dst;
    public int length;
    public String info;

    public ParsedPacket(int id, String time, String protocol,
                        String src, String dst, int length, String info) {
        this.id = id;
        this.time = time;
        this.protocol = protocol;
        this.src = src;
        this.dst = dst;
        this.length = length;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getSrc() {
        return src;
    }

    public String getDst() {
        return dst;
    }

    public int getLength() {
        return length;
    }

    public String getInfo() {
        return info;
    }
}
