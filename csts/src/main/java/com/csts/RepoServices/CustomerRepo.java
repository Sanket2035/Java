package com.csts.RepoServices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csts.Models.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
