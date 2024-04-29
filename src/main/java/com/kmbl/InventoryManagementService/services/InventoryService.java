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


    public InventoryResponseBody updateInventoryforOrder(List<OrderRequestBody> orderRequestItems) throws ServiceException {

            validateOrderRequestItems(orderRequestItems);
            InventoryOrderStatus overallStatus = InventoryOrderStatus.FAILED_ORDER;
            InventoryResponseBody response = new InventoryResponseBody();
            for (int i = 0; i < orderRequestItems.size(); i++) {
                Optional<Inventory> tempInventory = inventoryRepository.findBySellerIdAndProductId(orderRequestItems.get(i).getSellerID(), orderRequestItems.get(i).getProductID());
                Inventory inventoryFinal = null;
                if (tempInventory.isEmpty()) {
                    InventoryOrderItem tempInventoryOrderItem = new InventoryOrderItem(orderRequestItems.get(i).getProductID(), orderRequestItems.get(i).getSellerID(), String.valueOf(orderRequestItems.get(i).getCount()));
                    InventoryResponseObject inventoryresponseObject = new InventoryResponseObject("FAILED_ORDER", tempInventoryOrderItem, 0);
                    response.addResponseObject(inventoryresponseObject);
                } else {
                    inventoryFinal = tempInventory.get();
                    InventoryResponseObject inventoryresponseObject = getResponseObjectofUpdateInventory(inventoryFinal, orderRequestItems.get(i));
                    response.addResponseObject(inventoryresponseObject);
                    if (inventoryresponseObject.getOrderItemStatus() == "PARTIAL_ORDER" || inventoryresponseObject.getOrderItemStatus() == "COMPLETE_ORDER") {
                        overallStatus = InventoryOrderStatus.PARTIAL_ORDER;
                    }
                }
            }
            response.setInventoryOrderStatus(overallStatus);
            return response;

    }

    private void validateOrderRequestItems(List<OrderRequestBody> orderRequestItems) throws ServiceException {

        for (OrderRequestBody item : orderRequestItems) {
            if (item.getSellerID() == null || item.getSellerID().isEmpty())
                throw new ServiceException(ServiceException.Type.BAD_REQUEST, "SELLER ID IS NOT PRESENT");
            if (item.getProductID() == null || item.getProductID().isEmpty())
                throw new ServiceException(ServiceException.Type.BAD_REQUEST, "PRODUCT ID IS NOT PRESENT");
            if (item.getCount() == null || item.getCount() < 0)
                throw new ServiceException(ServiceException.Type.BAD_REQUEST, "COUNT IS EITHER NOT PRESENT OR NEGATIVE ");

        }
    }

    public InventoryResponseObject getResponseObjectofUpdateInventory(Inventory inventoryFinal, OrderRequestBody orderRequestItems) {
        Integer tempCount = 0;
        InventoryOrderStatus tempStatus = InventoryOrderStatus.FAILED_ORDER;
        if (inventoryFinal == null || inventoryFinal.getQuantity() == 0) {
            InventoryOrderItem tempInventoryOrderItem = new InventoryOrderItem(orderRequestItems.getProductID(), orderRequestItems.getSellerID(), String.valueOf(orderRequestItems.getCount()));
            InventoryResponseObject inventoryresponseObject = new InventoryResponseObject(String.valueOf(InventoryOrderStatus.FAILED_ORDER), tempInventoryOrderItem, 0);
            return inventoryresponseObject;
        }
        inventoryFinal.setQuantity(orderRequestItems.getCount() < inventoryFinal.getQuantity() ? inventoryFinal.getQuantity() - orderRequestItems.getCount() : 0);
        inventoryRepository.save(inventoryFinal);
        InventoryResponseObject inventoryresponseObject = new InventoryResponseObject(String.valueOf((tempCount == orderRequestItems.getCount()) ? InventoryOrderStatus.COMPLETE_ORDER : InventoryOrderStatus.PARTIAL_ORDER), new InventoryOrderItem(orderRequestItems.getProductID(), orderRequestItems.getSellerID(), String.valueOf(orderRequestItems.getCount())), Math.min(inventoryFinal.getQuantity(), orderRequestItems.getCount()));
        return inventoryresponseObject;

    }

}
