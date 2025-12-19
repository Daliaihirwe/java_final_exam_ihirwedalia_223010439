package com.emms.dao.impl;

import com.emms.dao.OutageDao;
import com.emms.model.Outage;
import com.emms.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OutageDaoImpl implements OutageDao {

    @Override
    public void add(Outage outage) {
        String sql = "INSERT INTO outage (meter_id, start_time, end_time, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, outage.getMeterId());
            pstmt.setTimestamp(2, outage.getStartTime());
            pstmt.setTimestamp(3, outage.getEndTime());
            pstmt.setString(4, outage.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Outage> findById(int id) {
        String sql = "SELECT * FROM outage WHERE outage_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToOutage(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Outage> findAll() {
        List<Outage> outages = new ArrayList<>();
        String sql = "SELECT * FROM outage ORDER BY start_time DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                outages.add(mapRowToOutage(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outages;
    }

    @Override
    public void update(Outage outage) {
        String sql = "UPDATE outage SET meter_id = ?, start_time = ?, end_time = ?, description = ? WHERE outage_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, outage.getMeterId());
            pstmt.setTimestamp(2, outage.getStartTime());
            pstmt.setTimestamp(3, outage.getEndTime());
            pstmt.setString(4, outage.getDescription());
            pstmt.setInt(5, outage.getOutageId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM outage WHERE outage_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Outage> findByMeterId(int meterId) {
        List<Outage> outages = new ArrayList<>();
        String sql = "SELECT * FROM outage WHERE meter_id = ? ORDER BY start_time DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, meterId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                outages.add(mapRowToOutage(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outages;
    }

    private Outage mapRowToOutage(ResultSet rs) throws SQLException {
        Outage outage = new Outage();
        outage.setOutageId(rs.getInt("outage_id"));
        outage.setMeterId(rs.getInt("meter_id"));
        outage.setStartTime(rs.getTimestamp("start_time"));
        outage.setEndTime(rs.getTimestamp("end_time"));
        outage.setDescription(rs.getString("description"));
        return outage;
    }
}