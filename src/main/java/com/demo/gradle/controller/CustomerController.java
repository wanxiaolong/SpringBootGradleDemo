package com.demo.gradle.controller;

import com.demo.gradle.entity.Customer;
import com.demo.gradle.service.CustomerService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
  @Autowired private CustomerService service;

  // 创建 Customer
  @PostMapping
  public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
    Customer savedCustomer = service.createCustomer(customer);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedCustomer.getId())
            .toUri();
    return ResponseEntity.created(location).body(savedCustomer);
  }

  // 获取所有 Customer
  @GetMapping
  public List<Customer> getAllCustomers() {
    return service.getAllCustomers();
  }

  // 根据 ID 获取 Customer
  @GetMapping("/{id}")
  public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
    return service
        .getCustomerById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // 更新 Customer
  @PutMapping("/{id}")
  public ResponseEntity<Customer> updateCustomer(
      @PathVariable Long id, @RequestBody Customer customerDetails) {
    try {
      Customer updatedCustomer = service.updateCustomer(id, customerDetails);
      return ResponseEntity.ok(updatedCustomer);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // 删除 Customer
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
    service.deleteCustomer(id);
    return ResponseEntity.noContent().build();
  }

  // 根据名字查找
  @GetMapping("/search")
  public List<Customer> findByFirstName(@RequestParam String firstName) {
    return service.findByFirstName(firstName);
  }
}
