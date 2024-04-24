package com.kmbl.InventoryManagementService.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.kmbl.InventoryManagementService.exceptions.ServiceException;
import com.kmbl.InventoryManagementService.models.kafka.CancelOrderMessage;
import lombok.extern.slf4j.Slf4j;
import com.kmbl.InventoryManagementService.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmbl.InventoryManagementService.models.Inventory;
import com.kmbl.InventoryManagementService.repositories.InventoryRepository;

@Slf4j
@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    //Get All Inventory Items
    public Iterable<Inventory> getAllInventoryItems() {
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
    public Inventory createInventoryItem(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventoryItem(String id, Inventory updatedInventory) {
        updatedInventory.setId(id);

        return inventoryRepository.save(updatedInventory);
    }

    public void freeInventory(CancelOrderMessage message) throws ServiceException {

        //TODO:: This code may failure on concurrent retries should issue idempotency create event logs

        List<CancelOrderMessage.OrderItem> missingRecords = new ArrayList<>();
        for (CancelOrderMessage.OrderItem item : message.getOrderItems()) {
            Inventory record = inventoryRepository
                    .findBySellerIdAndProductId(item.getSellerId(), item.getProductId()).orElse(null);
            if(record != null) {
                int newQuantity = record.getQuantity() + item.getQuantity();
                record.setQuantity(newQuantity);
                inventoryRepository.save(record);

            }else {
                missingRecords.add(item);
            }
        }
        if(!missingRecords.isEmpty() ){
            String errorMessage = String.join(",", missingRecords.stream().map( e -> String.format("( PId %s , SId %s)",e.getProductId(),e.getSellerId())).collect(Collectors.toSet()));
            throw new ServiceException("Unable to add backTo inventory" + errorMessage);
        }

    }

    public void deleteInventory(String id) {
        inventoryRepository.deleteById(id);
    }
}
