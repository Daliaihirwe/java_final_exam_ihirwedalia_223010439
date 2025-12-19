package com.emms.dao;

import com.emms.model.Maintenance;
import java.util.List;
import java.util.Optional;

public interface MaintenanceDao {
    void add(Maintenance maintenance);
    Optional<Maintenance> findById(int id);
    List<Maintenance> findAll();
    void update(Maintenance maintenance);
    void delete(int id);
    List<Maintenance> findByMeterId(int meterId);
}
