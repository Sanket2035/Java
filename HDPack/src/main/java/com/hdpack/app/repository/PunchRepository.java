package com.hdpack.app.repository;

import com.hdpack.app.model.Punch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PunchRepository extends MongoRepository<Punch, String> {
    // Find specific punch shape (e.g., "Circle 50mm")
    List<Punch> findByType(String type);
}