package com.Shadow.JWTProject.controllers;

import com.Shadow.JWTProject.entities.Order;
import com.Shadow.JWTProject.entities.Customer;
import com.Shadow.JWTProject.entities.Product;
import com.Shadow.JWTProject.services.OrderService;
import com.Shadow.JWTProject.services.CustomerService;
import com.Shadow.JWTProject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;

    @Autowired
    public OrderController(OrderService orderService,
            CustomerService customerService,
            ProductService productService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("orderForm", new Order());
        model.addAttribute("listCustomers", customerService.getAll());
        model.addAttribute("listProducts", productService.getAllProducts());
        return "orders/order_form";
    }

    @PostMapping("/save")
    public String saveOrder(@ModelAttribute("orderForm") Order order) {
        order.setDate(LocalDate.now());
        orderService.create(order);
        return "redirect:/orders/all";
    }

    @GetMapping("/all")
    public String listOrders(Model model) {
        List<Order> listOrders = orderService.getAll();
        model.addAttribute("listOrders", listOrders);
        return "orders/orders_list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Order order = orderService.getById(id);

        // Ensure customer is not null
        if (order.getCustomer() == null) {
            order.setCustomer(new Customer());
        }

        model.addAttribute("orderForm", order);
        model.addAttribute("listCustomers", customerService.getAll());
        model.addAttribute("listProducts", productService.getAllProducts());
        return "orders/order_edit_form";
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute("orderForm") Order order) {
        orderService.update(order.getId(), order);
        return "redirect:/orders/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/orders/all";
    }
}
