package com.emms.dao;

import com.emms.model.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    void add(Customer customer);
    Optional<Customer> findById(int id);
    List<Customer> findAll();
    void update(Customer customer);
    void delete(int id);
}
