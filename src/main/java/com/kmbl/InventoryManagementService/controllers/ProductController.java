package com.kmbl.InventoryManagementService.controllers;

import com.kmbl.InventoryManagementService.models.Product;
import com.kmbl.InventoryManagementService.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    private ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping(value = "{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable String productId) {
        Product product = productService.getProductById(productId);
        if(product == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
