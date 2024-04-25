package com.kmbl.InventoryManagementService.services;

import com.kmbl.InventoryManagementService.exceptions.ResourceNotFoundException;
import com.kmbl.InventoryManagementService.exceptions.ServiceException;
import com.kmbl.InventoryManagementService.models.*;
import com.kmbl.InventoryManagementService.models.kafka.CancelOrderMessage;
import com.kmbl.InventoryManagementService.repositories.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
        if (optionalInventory.isPresent()) {
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
            if (record != null) {
                int newQuantity = record.getQuantity() + item.getQuantity();
                record.setQuantity(newQuantity);
                inventoryRepository.save(record);

            } else {
                missingRecords.add(item);
            }
        }
        if (!missingRecords.isEmpty()) {
            String errorMessage = String.join(",", missingRecords.stream().map(e -> String.format("( PId %s , SId %s)", e.getProductId(), e.getSellerId())).collect(Collectors.toSet()));
            throw new ServiceException("Unable to add backTo inventory" + errorMessage);
        }

    }

    public void deleteInventory(String id) {
        inventoryRepository.deleteById(id);
    }


    public ResponseBody updateInventoryforOrder(List<OrderRequestBody> orderRequestItems) {
        synchronized (this) {
            InventoryOrderStatus overallStatus = InventoryOrderStatus.FAILED_ORDER;
            ResponseBody response = new ResponseBody();
            for (int i = 0; i < orderRequestItems.size(); i++) {
                Optional<Inventory> tempInventory = inventoryRepository.findBySellerIdAndProductId(orderRequestItems.get(i).getSellerID(),orderRequestItems.get(i).getProductID());
                Inventory inventoryFinal = null;
                if(tempInventory.isEmpty()){
                    InventoryOrderItem tempInventoryOrderItem =new InventoryOrderItem(orderRequestItems.get(i).getProductID(), orderRequestItems.get(i).getSellerID(), String.valueOf(orderRequestItems.get(i).getCount()));
                    ResponseObject responseObject =new ResponseObject("FAILED_ORDER",tempInventoryOrderItem,0);
                    response.addResponseObject(responseObject);
                }
                else {
                    inventoryFinal = tempInventory.get();
                    ResponseObject responseObject = getResponseObjectofUpdateInventory(inventoryFinal, orderRequestItems.get(i));
                    response.addResponseObject(responseObject);
                    if (responseObject.getStatus() == "PARTIAL_ORDER" || responseObject.getStatus() == "COMPLETE_ORDER") {
                        overallStatus = InventoryOrderStatus.PARTIAL_ORDER;
                    }
                }
            }
            response.setInventoryOrderStatus(overallStatus);
            return response;
        }
    }

    public ResponseObject getResponseObjectofUpdateInventory(Inventory inventoryFinal, OrderRequestBody orderRequestItems) {
        Integer tempCount = 0;
        InventoryOrderStatus tempStatus = InventoryOrderStatus.FAILED_ORDER;
        if (inventoryFinal == null || inventoryFinal.getQuantity() == 0) {
            tempCount = 0;
            tempStatus = InventoryOrderStatus.FAILED_ORDER;
        } else {
            int orderCountNeeded = orderRequestItems.getCount();
            tempCount = Math.min(inventoryFinal.getQuantity(), orderCountNeeded);
            tempStatus = (tempCount == orderRequestItems.getCount()) ? InventoryOrderStatus.COMPLETE_ORDER : InventoryOrderStatus.PARTIAL_ORDER;
            int inventoryUpdatedCount = inventoryFinal.getQuantity() - orderCountNeeded;
            int newQuantity = orderCountNeeded < inventoryFinal.getQuantity() ? inventoryUpdatedCount : 0;
            inventoryFinal.setQuantity(newQuantity);
            inventoryRepository.save(inventoryFinal);
        }
        InventoryOrderItem tempInventoryOrderItem = new InventoryOrderItem(orderRequestItems.getProductID(), orderRequestItems.getSellerID(), String.valueOf(orderRequestItems.getCount()));
        ResponseObject responseObject = new ResponseObject(String.valueOf(tempStatus), tempInventoryOrderItem, tempCount);
        return responseObject;
    }

}
