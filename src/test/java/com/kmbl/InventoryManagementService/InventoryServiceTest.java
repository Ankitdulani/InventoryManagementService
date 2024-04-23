package com.kmbl.InventoryManagementService;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kmbl.InventoryManagementService.models.Inventory;
import com.kmbl.InventoryManagementService.repositories.InventoryRepository;
import com.kmbl.InventoryManagementService.services.InventoryService;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    public void testFindAllInventoryItems(){
        Inventory item1 = new Inventory();
        item1.setId("1L");
        item1.setProductId("1L");
        item1.setPrice(100);
        item1.setQuantity(10);
        
        Inventory item2 = new Inventory();
        item2.setId("2L");
        item2.setProductId("2L");
        item2.setPrice(200);
        item2.setQuantity(20);

        Iterable<Inventory> expectedItems =Arrays.asList(item1, item2);
        when(inventoryRepository.findAll()).thenReturn(expectedItems);

        Iterable<Inventory> actualItems = inventoryService.getAllInventoryItems();

        assertEquals(expectedItems, actualItems, "should return the same items");

    }

    @Test
    public void testFindInventoryItemById(){
        Inventory expectedItem = new Inventory();
        expectedItem.setId("1L");
        expectedItem.setProductId("1L");
        expectedItem.setPrice(100);
        expectedItem.setQuantity(10);
        

        when(inventoryRepository.findById("1L")).thenReturn(Optional.of(expectedItem));

        Optional<Inventory> actualOptionalItem = inventoryService.getInventoryItemById("1L");

        Inventory actualItem = actualOptionalItem.orElse(null);
        
        assertEquals(expectedItem, actualItem, "should return the same item");

    }

    @Test
    public void testCreateInventoryItem(){
        //Mock data
        Inventory newItem = new Inventory();
        newItem.setId("1L");
        newItem.setProductId("1L");
        newItem.setPrice(100);
        newItem.setQuantity(10);

        when(inventoryRepository.save(newItem)).thenReturn(newItem);

        Inventory savedItem = inventoryService.createInventoryItem(newItem);

        assertNotNull(savedItem, "Saved item should not be null");
        assertEquals(newItem, savedItem, "Saved item should match");
    }

    @Test
    public void testUpdateInventoryItem(){
        //Mock Data
        Inventory existingItem = new Inventory();
        existingItem.setId("1L");
        existingItem.setProductId("1L");
        existingItem.setPrice(100);
        existingItem.setQuantity(10);

        Inventory updatedItem = new Inventory();
        updatedItem.setId("1L");
        updatedItem.setProductId("1L");
        updatedItem.setPrice(100);
        updatedItem.setQuantity(2);

        // when(inventoryRepository.findById("1L")).thenReturn(Optional.of(existingItem));
        when(inventoryRepository.save(updatedItem)).thenReturn(updatedItem);

        Inventory result = inventoryService.updateInventoryItem("1L", updatedItem);

        assertNotNull(result, "Result should not be null");
        assertEquals(updatedItem, result, "Updated item should match");

        verify(inventoryRepository, times(1)).save(updatedItem);

    }

    @Test
    public void testDeleteInventoryItem(){
        inventoryService.deleteInventory("1L");

        verify(inventoryRepository, times(1)).deleteById("1L");
    }
    
}
