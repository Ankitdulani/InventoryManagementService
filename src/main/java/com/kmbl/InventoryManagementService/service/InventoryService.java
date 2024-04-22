package com.kmbl.InventoryManagementService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kmbl.InventoryManagementService.models.Inventory;
import com.kmbl.InventoryManagementService.repository.InventoryRepository;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;
    }

    //Get All Inventory Items
    public List<Inventory> getAllInventoryItems(){
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

    public Inventory updateInventoryItem(String id, Inventory inventory){
        Optional<Inventory> existingItemOptional = inventoryRepository.findById(id);
        if(existingItemOptional.isEmpty()){
            return null;
        }
        Inventory existingItem = existingItemOptional.get();
        existingItem.setQuantity(inventory.getQuantity());
        existingItem.setPrice(inventory.getPrice());

        return inventoryRepository.save(existingItem);
    }

    public void deleteInventory(String id){
        inventoryRepository.deleteById(id);
    }
}
