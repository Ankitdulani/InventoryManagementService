package com.kmbl.InventoryManagementService.services;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmbl.InventoryManagementService.models.Inventory;
import com.kmbl.InventoryManagementService.repositories.InventoryRepository;

@Service
public class InventoryService {
    private static final Logger logger = LogManager.getLogger(InventoryService.class);
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;
    }

    //Get All Inventory Items
    public Iterable<Inventory> getAllInventoryItems(){
        logger.info("Fetching All Inventory Items");
        return inventoryRepository.findAll(); 
    }

    //Get inventory item by ID
    public Optional<Inventory> getInventoryItemById(String id){
        logger.info("Fetching Inventory Item by ID");
        return inventoryRepository.findById(id);
    }

    //create a new inventory
    public Inventory createInventoryItem(Inventory inventory){
        logger.info("Creating New Inventory Item");
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventoryItem(String id, Inventory updatedInventory){
        logger.info("Updating Inventory Item");
        updatedInventory.setId(id);

        return inventoryRepository.save(updatedInventory);
    }

    public void deleteInventory(String id){
        logger.warn("Deleting Inventory Item");
        inventoryRepository.deleteById(id);
    }
}
