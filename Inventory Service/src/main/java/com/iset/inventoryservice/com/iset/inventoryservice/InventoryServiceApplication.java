package com.iset.inventoryservice.com.iset.inventoryservice;

import com.iset.inventoryservice.com.iset.inventoryservice.entity.Product;
import com.iset.inventoryservice.com.iset.inventoryservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedInventory(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                productRepository.save(new Product(null, "Laptop", 100));
                productRepository.save(new Product(null, "Phone", 200));
                productRepository.save(new Product(null, "Headphones", 150));
                productRepository.save(new Product(null, "Monitor", 80));
            }
        };
    }
}