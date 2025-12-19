package com.emms.dao.impl;

import com.emms.dao.MaintenanceDao;
import com.emms.model.Maintenance;
import com.emms.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MaintenanceDaoImpl implements MaintenanceDao {

    @Override
    public void add(Maintenance maintenance) {
        String sql = "INSERT INTO maintenance (meter_id, maintenance_date, technician_name, notes) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maintenance.getMeterId());
            pstmt.setTimestamp(2, maintenance.getMaintenanceDate());
            pstmt.setString(3, maintenance.getTechnicianName());
            pstmt.setString(4, maintenance.getNotes());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Maintenance> findById(int id) {
        String sql = "SELECT * FROM maintenance WHERE maintenance_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToMaintenance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Maintenance> findAll() {
        List<Maintenance> records = new ArrayList<>();
        String sql = "SELECT * FROM maintenance ORDER BY maintenance_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                records.add(mapRowToMaintenance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public void update(Maintenance maintenance) {
        String sql = "UPDATE maintenance SET meter_id = ?, maintenance_date = ?, technician_name = ?, notes = ? WHERE maintenance_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maintenance.getMeterId());
            pstmt.setTimestamp(2, maintenance.getMaintenanceDate());
            pstmt.setString(3, maintenance.getTechnicianName());
            pstmt.setString(4, maintenance.getNotes());
            pstmt.setInt(5, maintenance.getMaintenanceId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM maintenance WHERE maintenance_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Maintenance> findByMeterId(int meterId) {
        List<Maintenance> records = new ArrayList<>();
        String sql = "SELECT * FROM maintenance WHERE meter_id = ? ORDER BY maintenance_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, meterId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                records.add(mapRowToMaintenance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    private Maintenance mapRowToMaintenance(ResultSet rs) throws SQLException {
        Maintenance maintenance = new Maintenance();
        maintenance.setMaintenanceId(rs.getInt("maintenance_id"));
        maintenance.setMeterId(rs.getInt("meter_id"));
        maintenance.setMaintenanceDate(rs.getTimestamp("maintenance_date"));
        maintenance.setTechnicianName(rs.getString("technician_name"));
        maintenance.setNotes(rs.getString("notes"));
        return maintenance;
    }
}