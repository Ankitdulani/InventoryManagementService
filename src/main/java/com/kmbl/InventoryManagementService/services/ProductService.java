package com.kmbl.InventoryManagementService.services;

import com.kmbl.InventoryManagementService.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import com.kmbl.InventoryManagementService.models.Product;
import com.kmbl.InventoryManagementService.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(String productId) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId).orElse(null);
        if(product == null) {
            throw new ResourceNotFoundException("Product Id : {} is not present");
          }
        return product;
    }
}
