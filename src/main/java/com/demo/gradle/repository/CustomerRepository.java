package com.demo.gradle.repository;

import com.demo.gradle.entity.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
  List<Customer> findByFirstName(String firstName);
}
