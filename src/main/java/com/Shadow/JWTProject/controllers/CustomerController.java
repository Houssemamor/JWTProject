package com.Shadow.JWTProject.controllers;

import com.Shadow.JWTProject.entities.Customer;
import com.Shadow.JWTProject.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("customerForm", new Customer());
        return "customers/customer_form";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customerForm") Customer customer) {
        customerService.create(customer);
        return "redirect:/customers/all";
    }

    @GetMapping("/all")
    public String listCustomers(Model model) {
        List<Customer> listCustomers = customerService.getAll();
        model.addAttribute("listCustomers", listCustomers);
        return "customers/customers_list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getById(id);
        model.addAttribute("customerForm", customer);
        return "customers/customer_edit_form";
    }

    @PostMapping("/update")
    public String updateCustomer(@ModelAttribute("customerForm") Customer customer) {
        customerService.update(customer.getId(), customer);
        return "redirect:/customers/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return "redirect:/customers/all";
    }
}