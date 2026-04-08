package com.hdpack.app.repository;

import com.hdpack.app.model.Ink;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InkRepository extends MongoRepository<Ink, String> {
    // Find ink by color (e.g., "Cyan")
    List<Ink> findByColorName(String colorName);
}