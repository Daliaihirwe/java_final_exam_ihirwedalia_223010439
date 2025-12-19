package com.emms.dao;

import com.emms.model.Outage;
import java.util.List;
import java.util.Optional;

public interface OutageDao {
    void add(Outage outage);
    Optional<Outage> findById(int id);
    List<Outage> findAll();
    void update(Outage outage);
    void delete(int id);
    List<Outage> findByMeterId(int meterId);
}
