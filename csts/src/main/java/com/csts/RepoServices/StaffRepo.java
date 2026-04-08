package com.csts.RepoServices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csts.Models.Staff;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Long> {

}