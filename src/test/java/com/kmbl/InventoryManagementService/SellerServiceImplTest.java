package com.kmbl.InventoryManagementService;

import com.kmbl.InventoryManagementService.models.Seller;
import com.kmbl.InventoryManagementService.repositories.SellerRepository;
import com.kmbl.InventoryManagementService.services.SellerServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SellerServiceImplTest {

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private SellerServiceImpl sellerService;

    @Test
    void testCreateSeller() {
        Seller seller = new Seller();
        seller.setId("1");
        seller.setName("Test");
        seller.setAddress("Test Address");
        seller.setRating(3);
        
        // Mock behavior of sellerRepository.save
        when(sellerRepository.save(seller)).thenReturn(seller);
        
        // Call the service method
        sellerService.createSeller(seller);
        
        // Verify that sellerRepository.save was called once with the seller object
        verify(sellerRepository, times(1)).save(seller);
    }

    @Test
    void testUpdateSeller() {
        Seller existingSeller = new Seller();
        existingSeller.setId("1");
        existingSeller.setName("Test");
        existingSeller.setAddress("Test Address");
        existingSeller.setRating(3);

        Seller updatedSeller = new Seller();
        updatedSeller.setId("1");
        updatedSeller.setName("Test 2");
        updatedSeller.setAddress("Test Address");
        updatedSeller.setRating(3);

        // Mock behavior of sellerRepository.findById
        when(sellerRepository.findById("1")).thenReturn(Optional.of(existingSeller));

        // Mock behavior of sellerRepository.save
        when(sellerRepository.save(updatedSeller)).thenReturn(updatedSeller);

        // Call the service method
        sellerService.updateSeller("1", updatedSeller);

        // Verify that sellerRepository.findById was called once with "1"
        verify(sellerRepository, times(1)).findById("1");

        // Verify that sellerRepository.save was called once with the updatedSeller object
        verify(sellerRepository, times(1)).save(updatedSeller);
    }

    @Test
    void testDeleteSeller() {
        String sellerId = "1";

        // Call the service method
        sellerService.deleteSeller(sellerId);

        // Verify that sellerRepository.deleteById was called once with "1"
        verify(sellerRepository, times(1)).deleteById(sellerId);
    }

    @Test
    void testGetSeller() {
        Seller existingSeller = new Seller();
        existingSeller.setId("1");
        existingSeller.setName("Test");
        existingSeller.setAddress("Test Address");
        existingSeller.setRating(3);

        // Mock behavior of sellerRepository.findById
        when(sellerRepository.findById("1")).thenReturn(Optional.of(existingSeller));

        // Call the service method
        Seller result = sellerService.getSeller("1");

        // Verify that sellerRepository.findById was called once with "1"
        verify(sellerRepository, times(1)).findById("1");

        // Assert the result
        assertNotNull(result, "Result should not be null");
        assertEquals(existingSeller, result, "Seller should match");
    }

    @Test
    void testGetAllSellers() {
        Seller seller1 = new Seller();
        seller1.setId("1");
        seller1.setName("Test");
        seller1.setAddress("Test Address");
        seller1.setRating(3);

        Seller seller2 = new Seller();
        seller2.setId("1");
        seller2.setName("Test 2");
        seller2.setAddress("Test Address");
        seller2.setRating(3);
        List<Seller> sellers = new ArrayList<>();
        sellers.add(seller1);
        sellers.add(seller2);

        // Mock behavior of sellerRepository.findAll
        when(sellerRepository.findAll()).thenReturn(sellers);

        // Call the service method
        List<Seller> result = sellerService.getAllSellers();

        // Verify that sellerRepository.findAll was called once
        verify(sellerRepository, times(1)).findAll();

        // Assert the result
        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size(), "Result size should be 2");
        assertEquals(sellers, result, "Sellers should match");
    }
}