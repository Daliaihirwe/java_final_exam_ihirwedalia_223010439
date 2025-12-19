package com.emms.dao.impl;

import com.emms.dao.MeterDao;
import com.emms.model.Meter;
import com.emms.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MeterDaoImpl implements MeterDao {

    @Override
    public void add(Meter meter) {
        String sql = "INSERT INTO meter (serial_number, installation_date, customer_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meter.getSerialNumber());
            pstmt.setTimestamp(2, meter.getInstallationDate());
            pstmt.setInt(3, meter.getCustomerId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Meter> findById(int id) {
        String sql = "SELECT * FROM meter WHERE meter_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToMeter(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Meter> findAll() {
        List<Meter> meters = new ArrayList<>();
        String sql = "SELECT * FROM meter ORDER BY meter_id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                meters.add(mapRowToMeter(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meters;
    }

    @Override
    public void update(Meter meter) {
        String sql = "UPDATE meter SET serial_number = ?, installation_date = ?, customer_id = ? WHERE meter_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, meter.getSerialNumber());
            pstmt.setTimestamp(2, meter.getInstallationDate());
            pstmt.setInt(3, meter.getCustomerId());
            pstmt.setInt(4, meter.getMeterId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM meter WHERE meter_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Meter> findByCustomerId(int customerId) {
        List<Meter> meters = new ArrayList<>();
        String sql = "SELECT * FROM meter WHERE customer_id = ? ORDER BY meter_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                meters.add(mapRowToMeter(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meters;
    }

    private Meter mapRowToMeter(ResultSet rs) throws SQLException {
        Meter meter = new Meter();
        meter.setMeterId(rs.getInt("meter_id"));
        meter.setSerialNumber(rs.getString("serial_number"));
        meter.setInstallationDate(rs.getTimestamp("installation_date"));
        meter.setCustomerId(rs.getInt("customer_id"));
        return meter;
    }
}