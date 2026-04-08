package com.hdpack.app.repository;

import com.hdpack.app.model.Paper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaperRepository extends MongoRepository<Paper, String> {
    // Find all papers of a specific type (e.g., "Glossy")
    List<Paper> findByType(String type);
    
    // Check if we already have this specific paper brand/size
    boolean existsByBrandAndWidth(String brand, double width);
}