package com.emms.dao.impl;

import com.emms.dao.PlanDao;
import com.emms.model.Plan;
import com.emms.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlanDaoImpl implements PlanDao {

    @Override
    public void add(Plan plan) {
        String sql = "INSERT INTO plan (name, rate_per_kwh, description) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plan.getName());
            pstmt.setBigDecimal(2, plan.getRatePerKwh());
            pstmt.setString(3, plan.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Plan> findById(int id) {
        String sql = "SELECT * FROM plan WHERE plan_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Plan plan = new Plan();
                plan.setPlanId(rs.getInt("plan_id"));
                plan.setName(rs.getString("name"));
                plan.setRatePerKwh(rs.getBigDecimal("rate_per_kwh"));
                plan.setDescription(rs.getString("description"));
                return Optional.of(plan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Plan> findAll() {
        List<Plan> plans = new ArrayList<>();
        String sql = "SELECT * FROM plan ORDER BY plan_id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Plan plan = new Plan();
                plan.setPlanId(rs.getInt("plan_id"));
                plan.setName(rs.getString("name"));
                plan.setRatePerKwh(rs.getBigDecimal("rate_per_kwh"));
                plan.setDescription(rs.getString("description"));
                plans.add(plan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plans;
    }

    @Override
    public void update(Plan plan) {
        String sql = "UPDATE plan SET name = ?, rate_per_kwh = ?, description = ? WHERE plan_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plan.getName());
            pstmt.setBigDecimal(2, plan.getRatePerKwh());
            pstmt.setString(3, plan.getDescription());
            pstmt.setInt(4, plan.getPlanId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM plan WHERE plan_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}