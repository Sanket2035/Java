package com.csts.RepoServices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csts.Models.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin, String> {

}