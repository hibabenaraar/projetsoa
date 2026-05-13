package com.example.shippingService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.shippingService.entity.Shipping;
import com.example.shippingService.repository.ShippingRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShippingService {

    @Autowired
    private ShippingRepository repository;

  
    public Shipping createShipping(Shipping shipping) {

    shipping.setStatus("PENDING");

    return repository.save(shipping);
}

    public List<Shipping> getAll() {
        return repository.findAll();
    }

    public Shipping getByOrderId(Long orderId) {
        return repository.findByOrderId(orderId);
    }

    public Shipping updateStatusByOrderId(Long orderId, String status) {
        Shipping s = repository.findByOrderId(orderId);

        if (s == null) {
            throw new RuntimeException("Shipping not found for orderId: " + orderId);
        }

        s.setStatus(status);

        if ("SHIPPED".equals(status)) {
            s.setShippingDate(LocalDate.now());
        }

        if ("DELIVERED".equals(status)) {
            s.setDeliveryDate(LocalDate.now());
        }

        return repository.save(s);
    }
}