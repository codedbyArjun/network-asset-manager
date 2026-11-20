package model;

import java.sql.Timestamp;

public class Host {
    private int id;
    private String ipAddress;
    private String hostname;
    private String operatingSystem;
    private String status;
    private String riskLevel;
    private String notes;
    private Timestamp createdAt;
    private int portCount;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getHostname() { return hostname; }
    public void setHostname(String hostname) { this.hostname = hostname; }

    public String getOperatingSystem() { return operatingSystem; }
    public void setOperatingSystem(String operatingSystem) { this.operatingSystem = operatingSystem; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public int getPortCount() { return portCount; }
    public void setPortCount(int portCount) { this.portCount = portCount; }
}
