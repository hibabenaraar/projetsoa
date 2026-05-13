package com.example.shippingService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.shippingService.entity.Shipping;
import com.example.shippingService.service.ShippingService;

import java.util.List;

@RestController
@RequestMapping("/shipping")
@CrossOrigin("*")
public class ShippingController {

    @Autowired
    private ShippingService service;

    @PostMapping("/create")
    public Shipping create(@RequestBody Shipping shipping) {
        return service.createShipping(shipping);
    }

    @GetMapping
    public List<Shipping> getAll() {
        return service.getAll();
    }

    @PutMapping("/update/order/{orderId}/{status}")
    public Shipping updateByOrderId(@PathVariable Long orderId,
                                   @PathVariable String status) {
        return service.updateStatusByOrderId(orderId, status.toUpperCase());
    }
}