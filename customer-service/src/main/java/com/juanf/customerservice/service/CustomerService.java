package com.juanf.customerservice.service;

import java.util.List;

import com.juanf.customerservice.entity.Customer;

public interface CustomerService {
	public List<Customer> findCustomerAll();
    //public List<Customer> findCustomersByRegion(Region region);

    public Customer createCustomer(Customer customer);
    public Customer updateCustomer(Customer customer);
    public Customer deleteCustomer(Customer customer);
    public Customer getCustomer(Long id);
}
