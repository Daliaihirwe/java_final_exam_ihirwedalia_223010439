package com.emms.dao;

import com.emms.model.Reading;
import java.util.List;
import java.util.Optional;

public interface ReadingDao {
    void add(Reading reading);
    Optional<Reading> findById(long id);
    List<Reading> findAll();
    void update(Reading reading);
    void delete(long id);
    List<Reading> findByMeterId(int meterId);
}
