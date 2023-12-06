package com.juanf.customerservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juanf.customerservice.entity.Customer;

public interface CustomerRepository  extends JpaRepository<Customer,Long> {
    public Customer findByRun(String run);
    public List<Customer> findByLastName(String lastName);

}
