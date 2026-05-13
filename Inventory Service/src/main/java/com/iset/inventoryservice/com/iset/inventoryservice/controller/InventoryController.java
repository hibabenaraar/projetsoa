package com.iset.inventoryservice.com.iset.inventoryservice.controller;

import com.iset.inventoryservice.com.iset.inventoryservice.entity.Product;
import com.iset.inventoryservice.com.iset.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@CrossOrigin("*")
public class InventoryController {

    @Autowired
    private InventoryService service;

    // ajouter produit
    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return service.addProduct(product);
    }

    // afficher produits
    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    // verifier stock
    @GetMapping("/check/{name}/{qty}")
    public Boolean checkStock(
            @PathVariable String name,
            @PathVariable Integer qty
    ) {
        return service.checkStock(name, qty);
    }
}