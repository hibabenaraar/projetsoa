package com.example.shippingService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.shippingService.entity.Shipping;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {
    Shipping findByOrderId(Long orderId);
}