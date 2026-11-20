package dao;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Host;

public class HostDAO {

    private void bind(PreparedStatement ps, Host h) throws SQLException {
        ps.setString(1, h.getIpAddress());
        ps.setString(2, h.getHostname().isEmpty() ? null : h.getHostname());
        ps.setString(3, h.getOperatingSystem().isEmpty() ? null : h.getOperatingSystem());
        ps.setString(4, h.getStatus());
        ps.setString(5, h.getRiskLevel());
        ps.setString(6, h.getNotes().isEmpty() ? null : h.getNotes());
    }

    private Host mapRow(ResultSet rs) throws SQLException {
        Host h = new Host();
        h.setId(rs.getInt("id"));
        h.setIpAddress(rs.getString("ip_address"));
        h.setHostname(rs.getString("hostname"));
        h.setOperatingSystem(rs.getString("operating_system"));
        h.setStatus(rs.getString("status"));
        h.setRiskLevel(rs.getString("risk_level"));
        h.setNotes(rs.getString("notes"));
        h.setCreatedAt(rs.getTimestamp("created_at"));
        try {
            h.setPortCount(rs.getInt("port_count"));
        } catch (SQLException ignored) {
            h.setPortCount(0);
        }
        return h;
    }

    public int add(Host h) throws SQLException {
        String sql = "INSERT INTO hosts (ip_address, hostname, operating_system, status, risk_level, notes) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bind(ps, h);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : -1;
            }
        }
    }

    public void update(Host h) throws SQLException {
        String sql = "UPDATE hosts SET ip_address = ?, hostname = ?, operating_system = ?, "
                   + "status = ?, risk_level = ?, notes = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            bind(ps, h);
            ps.setInt(7, h.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM hosts WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Host findById(int id) throws SQLException {
        String sql = "SELECT h.*, COUNT(p.id) AS port_count FROM hosts h "
                   + "LEFT JOIN ports p ON h.id = p.host_id WHERE h.id = ? GROUP BY h.id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }

    public List<Host> search(String query, String riskFilter) throws SQLException {
        StringBuilder sql = new StringBuilder(
            "SELECT h.*, COUNT(p.id) AS port_count FROM hosts h " +
            "LEFT JOIN ports p ON h.id = p.host_id WHERE 1=1 "
        );
        List<Object> params = new ArrayList<>();

        if (query != null && !query.trim().isEmpty()) {
            sql.append("AND (h.ip_address LIKE ? OR h.hostname LIKE ? OR h.operating_system LIKE ? OR h.notes LIKE ?) ");
            String like = "%" + query.trim() + "%";
            params.add(like);
            params.add(like);
            params.add(like);
            params.add(like);
        }

        if (riskFilter != null && !riskFilter.trim().isEmpty() && !"All".equalsIgnoreCase(riskFilter)) {
            sql.append("AND h.risk_level = ? ");
            params.add(riskFilter.trim());
        }

        sql.append("GROUP BY h.id ORDER BY h.created_at DESC, h.id DESC");

        List<Host> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public List<Host> findRecent(int limit) throws SQLException {
        String sql = "SELECT h.*, COUNT(p.id) AS port_count FROM hosts h "
                   + "LEFT JOIN ports p ON h.id = p.host_id "
                   + "GROUP BY h.id ORDER BY h.created_at DESC, h.id DESC LIMIT ?";
        List<Host> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    public Map<String, Integer> getDashboardStats() throws SQLException {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("total", 0);
        stats.put("active", 0);
        stats.put("inactive", 0);
        stats.put("high", 0);
        stats.put("medium", 0);
        stats.put("low", 0);
        stats.put("ports", 0);

        String hostSql = "SELECT COUNT(*) AS total, "
                       + "SUM(CASE WHEN status = 'Active' THEN 1 ELSE 0 END) AS active, "
                       + "SUM(CASE WHEN status = 'Inactive' THEN 1 ELSE 0 END) AS inactive, "
                       + "SUM(CASE WHEN risk_level = 'High' THEN 1 ELSE 0 END) AS high, "
                       + "SUM(CASE WHEN risk_level = 'Medium' THEN 1 ELSE 0 END) AS medium, "
                       + "SUM(CASE WHEN risk_level = 'Low' THEN 1 ELSE 0 END) AS low "
                       + "FROM hosts";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(hostSql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                stats.put("total", rs.getInt("total"));
                stats.put("active", rs.getInt("active"));
                stats.put("inactive", rs.getInt("inactive"));
                stats.put("high", rs.getInt("high"));
                stats.put("medium", rs.getInt("medium"));
                stats.put("low", rs.getInt("low"));
            }
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM ports");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                stats.put("ports", rs.getInt(1));
            }
        }
        return stats;
    }
}
