package com.Shadow.JWTProject.services;

import com.Shadow.JWTProject.entities.Order;
import com.Shadow.JWTProject.entities.Product;
import com.Shadow.JWTProject.entities.Customer;
import com.Shadow.JWTProject.repositories.OrderRepository;
import com.Shadow.JWTProject.repositories.ProductRepository;
import com.Shadow.JWTProject.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final CustomerRepository customerRepo;

    public OrderService(OrderRepository orderRepo,
            ProductRepository productRepo,
            CustomerRepository customerRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.customerRepo = customerRepo;
    }

    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    public Order getById(Long id) {
        return orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    public Order create(Order order) {
        resolveOrderRelations(order);
        return orderRepo.save(order);
    }

    public Order update(Long id, Order order) {
        Order existing = getById(id);
        existing.setRef(order.getRef());
        existing.setPrice(order.getPrice());
        existing.setDate(order.getDate());

        if (order.getProducts() != null) {
            existing.setProducts(resolveProducts(order.getProducts()));
        }

        if (order.getCustomer() != null && order.getCustomer().getId() != null) {
            Customer c = customerRepo.findById(order.getCustomer().getId())
                    .orElseThrow(() -> new RuntimeException("Customer not found: " + order.getCustomer().getId()));
            existing.setCustomer(c);
        }

        return orderRepo.save(existing);
    }

    public void delete(Long id) {
        orderRepo.deleteById(id);
    }

    private void resolveOrderRelations(Order order) {
        if (order.getProducts() != null && !order.getProducts().isEmpty()) {
            order.setProducts(resolveProducts(order.getProducts()));
        }

        if (order.getCustomer() != null && order.getCustomer().getId() != null) {
            Customer c = customerRepo.findById(order.getCustomer().getId())
                    .orElseThrow(() -> new RuntimeException("Customer not found: " + order.getCustomer().getId()));
            order.setCustomer(c);
        }
    }

    private List<Product> resolveProducts(List<Product> incomingProducts) {
        List<Long> ids = incomingProducts.stream()
                .map(Product::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (ids.isEmpty()) {
            // fallback: save provided product objects (not typical)
            return incomingProducts.stream()
                    .map(productRepo::save)
                    .collect(Collectors.toList());
        } else {
            List<Product> found = productRepo.findAllById(ids);
            if (found.size() != ids.size()) {
                throw new RuntimeException("Some products not found for ids: " + ids);
            }
            // preserve order of requested ids
            Map<Long, Product> map = found.stream().collect(Collectors.toMap(Product::getId, p -> p));
            return ids.stream().map(map::get).collect(Collectors.toList());
        }
    }
}
