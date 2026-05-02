/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.forgepacket.kashifVuln.Jamal;

/**
 *
 * @author Toxin
 */

import java.util.HashMap;
import java.util.Map;

/**
 * Maintains a mapping between IP addresses and user-defined names.
 */
public class IPRegistry {

    private final Map<String, String> ipMap;

    /**
     * Initializes the registry
     */
    public IPRegistry() {
        this.ipMap = new HashMap<>();
    }

    /**
     * Adds or updates an IP-to-name mapping.
     *
     * @param ip   IP address
     * @param name associated name
     */
    public void addMapping(String ip, String name) {
        ipMap.put(ip, name);
    }

    /**
     * Removes an IP mapping.
     *
     * @param ip IP address
     */
    public void removeMapping(String ip) {
        ipMap.remove(ip);
    }

    /**
     * Retrieves name associated with an IP.
     *
     * @param ip IP address
     * @return mapped name or "Unknown"
     */
    public String resolve(String ip) {
        return ipMap.getOrDefault(ip, "Unknown");
    }
}
