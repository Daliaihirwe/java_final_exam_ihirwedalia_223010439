package com.emms.dao;

import com.emms.model.Plan;
import java.util.List;
import java.util.Optional;

public interface PlanDao {
    void add(Plan plan);
    Optional<Plan> findById(int id);
    List<Plan> findAll();
    void update(Plan plan);
    void delete(int id);
}
