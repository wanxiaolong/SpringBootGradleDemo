package com.demo.gradle.service;

import com.demo.gradle.entity.Customer;
import com.demo.gradle.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
  @Autowired private CustomerRepository repository;

  // 创建 Customer
  public Customer createCustomer(Customer customer) {
    return repository.save(customer);
  }

  // 获取所有 Customer
  public List<Customer> getAllCustomers() {
    return repository.findAll();
  }

  // 根据 ID 获取 Customer
  public Optional<Customer> getCustomerById(Long id) {
    return repository.findById(id);
  }

  // 更新 Customer
  public Customer updateCustomer(Long id, Customer customerDetails) {
    return repository
        .findById(id)
        .map(
            existingCustomer -> {
              existingCustomer.setFirstName(customerDetails.getFirstName());
              existingCustomer.setLastName(customerDetails.getLastName());
              return repository.save(existingCustomer);
            })
        .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
  }

  // 删除 Customer
  public void deleteCustomer(Long id) {
    repository.deleteById(id);
  }

  // 根据姓氏查找
  public List<Customer> findByFirstName(String firstName) {
    return repository.findByFirstName(firstName);
  }
}
