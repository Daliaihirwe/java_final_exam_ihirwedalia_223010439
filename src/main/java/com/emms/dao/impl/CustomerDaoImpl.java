package com.emms.dao.impl;

import com.emms.dao.CustomerDao;
import com.emms.model.Customer;
import com.emms.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public void add(Customer customer) {
        String sql = "INSERT INTO customer (name, address, phone, email, plan_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getEmail());
            pstmt.setInt(5, customer.getPlanId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Customer> findById(int id) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToCustomer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer ORDER BY customer_id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customers.add(mapRowToCustomer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customer SET name = ?, address = ?, phone = ?, email = ?, plan_id = ? WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getEmail());
            pstmt.setInt(5, customer.getPlanId());
            pstmt.setInt(6, customer.getCustomerId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Customer mapRowToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setName(rs.getString("name"));
        customer.setAddress(rs.getString("address"));
        customer.setPhone(rs.getString("phone"));
        customer.setEmail(rs.getString("email"));
        customer.setPlanId(rs.getInt("plan_id"));
        return customer;
    }
}