package com.forgepacket.kashifVuln.Jamal.etc;

import java.io.Serializable;

public class Threat implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int id;
    private final String sourceIP;
    private final String type;
    private final String severity;
    private String status;

    public Threat(int id, String sourceIP, String type, String severity) {
        this.id = id;
        this.sourceIP = sourceIP;
        this.type = type;
        this.severity = severity;
        this.status = "ACTIVE";
    }

    public int getId() {
        return id;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public String getType() {
        return type;
    }

    public String getSeverity() {
        return severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}