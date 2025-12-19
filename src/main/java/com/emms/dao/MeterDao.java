package com.emms.dao;

import com.emms.model.Meter;
import java.util.List;
import java.util.Optional;

public interface MeterDao {
    void add(Meter meter);
    Optional<Meter> findById(int id);
    List<Meter> findAll();
    void update(Meter meter);
    void delete(int id);
    List<Meter> findByCustomerId(int customerId);
}
