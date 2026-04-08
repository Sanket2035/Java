package com.hdpack.app.repository;

import com.hdpack.app.model.Job;
import com.hdpack.app.model.enums.JobStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends MongoRepository<Job, String> {

    // 1. The Critical Lookup: Find a unique job by its sheet number
    // We use "Optional" because the job might not exist.
    Optional<Job> findByJobSheetNo(String jobSheetNo);

    // 2. The Duplicate Check: Used by validation logic
    // Before creating a job, we check: "Does this sheet number already exist?"
    boolean existsByJobSheetNo(String jobSheetNo);

    // 3. Status Reports: "Give me all PENDING jobs"
    // Useful for the Operator Dashboard
    List<Job> findByStatus(JobStatus status);

    // 4. Client Search: "Show me all jobs for 'Coca Cola'"
    // "ContainingIgnoreCase" allows you to search "coca" and still find it.
    List<Job> findByCompanyNameContainingIgnoreCase(String companyName);

    // 5. Advanced: Pagination (Strictly Professional)
    // If you have 50,000 completed jobs, you don't want to load them all at once.
    // usage: jobRepository.findByStatus(JobStatus.COMPLETED, PageRequest.of(0, 20));
    Page<Job> findByStatus(JobStatus status, Pageable pageable);
}