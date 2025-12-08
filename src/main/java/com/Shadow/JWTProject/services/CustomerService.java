package com.Shadow.JWTProject.services;

import com.Shadow.JWTProject.entities.Customer;
import com.Shadow.JWTProject.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public List<Customer> getAll() {
        return repo.findAll();
    }

    public Customer getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Customer not found: " + id));
    }

    public Customer create(Customer customer) {
        return repo.save(customer);
    }

    public Customer update(Long id, Customer customer) {
        Customer existing = getById(id);
        existing.setName(customer.getName());
        existing.setSalary(customer.getSalary());
        existing.setPhone(customer.getPhone());
        existing.setAge(customer.getAge());
        existing.setEmail(customer.getEmail());
        existing.setPassword(customer.getPassword());
        existing.setAddress(customer.getAddress());
        existing.setCity(customer.getCity());
        existing.setOrders(customer.getOrders());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
