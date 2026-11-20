package dao;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Port;

public class PortDAO {

    public List<Port> findByHostId(int hostId) throws SQLException {
        String sql = "SELECT id, host_id, port_number, service_name, vulnerability_note "
                   + "FROM ports WHERE host_id = ? ORDER BY port_number ASC";
        List<Port> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hostId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Port p = new Port();
                    p.setId(rs.getInt("id"));
                    p.setHostId(rs.getInt("host_id"));
                    p.setPortNumber(rs.getInt("port_number"));
                    p.setServiceName(rs.getString("service_name"));
                    p.setVulnerabilityNote(rs.getString("vulnerability_note"));
                    list.add(p);
                }
            }
        }
        return list;
    }

    public void add(Port p) throws SQLException {
        String sql = "INSERT INTO ports (host_id, port_number, service_name, vulnerability_note) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getHostId());
            ps.setInt(2, p.getPortNumber());
            ps.setString(3, p.getServiceName());
            ps.setString(4, (p.getVulnerabilityNote() == null || p.getVulnerabilityNote().isEmpty()) ? null : p.getVulnerabilityNote());
            ps.executeUpdate();
        }
    }

    public void delete(int portId, int hostId) throws SQLException {
        String sql = "DELETE FROM ports WHERE id = ? AND host_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, portId);
            ps.setInt(2, hostId);
            ps.executeUpdate();
        }
    }
}
