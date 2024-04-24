package com.kmbl.InventoryManagementService.services;

import java.util.Optional;

import com.kmbl.InventoryManagementService.exceptions.ResourceNotFoundException;
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
    public Inventory getInventoryItemById(String id) throws ResourceNotFoundException {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
        if(optionalInventory.isPresent()) {
          return optionalInventory.get();
        }
        throw new ResourceNotFoundException("Inventory Id : {} is not present");
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
