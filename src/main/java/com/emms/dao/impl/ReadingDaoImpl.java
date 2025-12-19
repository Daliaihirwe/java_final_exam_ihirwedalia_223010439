package com.emms.dao.impl;

import com.emms.dao.ReadingDao;
import com.emms.model.Reading;
import com.emms.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReadingDaoImpl implements ReadingDao {

    @Override
    public void add(Reading reading) {
        String sql = "INSERT INTO reading (meter_id, reading_timestamp, kwh_consumed) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reading.getMeterId());
            pstmt.setTimestamp(2, reading.getReadingTimestamp());
            pstmt.setBigDecimal(3, reading.getKwhConsumed());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Reading> findById(long id) {
        String sql = "SELECT * FROM reading WHERE reading_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToReading(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Reading> findAll() {
        List<Reading> readings = new ArrayList<>();
        String sql = "SELECT * FROM reading ORDER BY reading_timestamp DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                readings.add(mapRowToReading(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readings;
    }

    @Override
    public void update(Reading reading) {
        String sql = "UPDATE reading SET meter_id = ?, reading_timestamp = ?, kwh_consumed = ? WHERE reading_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reading.getMeterId());
            pstmt.setTimestamp(2, reading.getReadingTimestamp());
            pstmt.setBigDecimal(3, reading.getKwhConsumed());
            pstmt.setLong(4, reading.getReadingId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM reading WHERE reading_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reading> findByMeterId(int meterId) {
        List<Reading> readings = new ArrayList<>();
        String sql = "SELECT * FROM reading WHERE meter_id = ? ORDER BY reading_timestamp DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, meterId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                readings.add(mapRowToReading(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readings;
    }

    private Reading mapRowToReading(ResultSet rs) throws SQLException {
        Reading reading = new Reading();
        reading.setReadingId(rs.getLong("reading_id"));
        reading.setMeterId(rs.getInt("meter_id"));
        reading.setReadingTimestamp(rs.getTimestamp("reading_timestamp"));
        reading.setKwhConsumed(rs.getBigDecimal("kwh_consumed"));
        return reading;
    }
}