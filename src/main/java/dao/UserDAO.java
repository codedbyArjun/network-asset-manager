package dao;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import util.PasswordUtil;

public class UserDAO {
    public boolean existsByUsernameOrEmail(String username, String email) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE username = ? OR email = ? LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void createUser(String fullName, String username, String email, String rawPassword) throws SQLException {
        String sql = "INSERT INTO users (full_name, username, email, password_hash) VALUES (?, ?, ?, ?)";
        String hash = PasswordUtil.hashPassword(rawPassword);
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, hash);
            ps.executeUpdate();
        }
    }

    public User authenticate(String username, String rawPassword) throws SQLException {
        String sql = "SELECT id, full_name, username, email, password_hash, created_at FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    if (PasswordUtil.verifyPassword(rawPassword, storedHash)) {
                        User u = new User();
                        u.setId(rs.getInt("id"));
                        u.setFullName(rs.getString("full_name"));
                        u.setUsername(rs.getString("username"));
                        u.setEmail(rs.getString("email"));
                        u.setCreatedAt(rs.getTimestamp("created_at"));
                        return u;
                    }
                }
            }
        }
        return null;
    }
}
