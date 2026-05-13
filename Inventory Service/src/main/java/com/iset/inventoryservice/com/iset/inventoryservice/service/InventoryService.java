package com.iset.inventoryservice.com.iset.inventoryservice.service;

import com.iset.inventoryservice.com.iset.inventoryservice.entity.Product;
import com.iset.inventoryservice.com.iset.inventoryservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private ProductRepository repository;

    // ajouter produit
    public Product addProduct(Product product) {
        return repository.save(product);
    }

    // afficher produits
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductByName(String productName) {
        return repository.findByProductName(productName);
    }

    // verifier stock
    public Boolean checkStock(String productName, Integer qty) {

        Product product = repository.findByProductName(productName);

        if (product != null && product.getQuantity() >= qty) {

            product.setQuantity(
                    product.getQuantity() - qty
            );

            repository.save(product);

            return true;
        }

        return false;
    }
}