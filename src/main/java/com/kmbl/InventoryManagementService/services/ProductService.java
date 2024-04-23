package com.kmbl.InventoryManagementService.services;

import com.kmbl.InventoryManagementService.repositories.ProductRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.kmbl.InventoryManagementService.models.Product;

@Service
public class ProductService {
    private static final Logger logger = LogManager.getLogger(InventoryService.class);
    private final ProductRepository productRepository;

    private ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        logger.info("Creating New Product");
        return productRepository.save(product);
    }

    public Product getProductById(String productId) {
        logger.info("Get Product By Id");
        return productRepository.findById(productId).orElse(null);
    }
}
