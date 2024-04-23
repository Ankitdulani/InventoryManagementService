package com.kmbl.InventoryManagementService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kmbl.InventoryManagementService.models.Product;
import com.kmbl.InventoryManagementService.repositories.ProductRepository;
import com.kmbl.InventoryManagementService.services.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testCreateProduct(){
        //Mock data
        Product newItem = new Product();
        newItem.setId("1L");
        newItem.setBrand("NIKE");
        newItem.setCategory("SHOES");

        when(productRepository.save(newItem)).thenReturn(newItem);

        Product savedItem = productService.createProduct(newItem);

        assertNotNull(savedItem, "Saved item should not be null");
        assertEquals(newItem, savedItem, "Saved item should match");
    }

    @Test
    public void testFindProductById(){
        Product expectedItem = new Product();
        expectedItem.setId("1L");
        expectedItem.setBrand("NIKE");
        expectedItem.setCategory("SHOES");
        

        when(productRepository.findById("1L")).thenReturn(Optional.of(expectedItem));

        Product actualItem = productService.getProductById("1L");
        
        assertEquals(expectedItem, actualItem, "should return the same item");

    }

}
