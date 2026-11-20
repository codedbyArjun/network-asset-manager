package model;

public class Port {
    private int id;
    private int hostId;
    private int portNumber;
    private String serviceName;
    private String vulnerabilityNote;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getHostId() { return hostId; }
    public void setHostId(int hostId) { this.hostId = hostId; }

    public int getPortNumber() { return portNumber; }
    public void setPortNumber(int portNumber) { this.portNumber = portNumber; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getVulnerabilityNote() { return vulnerabilityNote; }
    public void setVulnerabilityNote(String vulnerabilityNote) { this.vulnerabilityNote = vulnerabilityNote; }
}
