package com.kmbl.InventoryManagementService.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmbl.InventoryManagementService.models.Inventory;
import com.kmbl.InventoryManagementService.repositories.InventoryRepository;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;
    }

    //Get All Inventory Items
    public Iterable<Inventory> getAllInventoryItems(){
        return inventoryRepository.findAll(); 
    }

    //Get inventory item by ID
    public Optional<Inventory> getInventoryItemById(String id){
        return inventoryRepository.findById(id);
    }

    //create a new inventory
    public Inventory createInventoryItem(Inventory inventory){
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventoryItem(String id, Inventory updatedInventory){
        updatedInventory.setId(id);

        return inventoryRepository.save(updatedInventory);
    }

    public void deleteInventory(String id){
        inventoryRepository.deleteById(id);
    }
}
