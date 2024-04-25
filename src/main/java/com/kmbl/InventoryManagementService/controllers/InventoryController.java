package com.kmbl.InventoryManagementService.controllers;

import com.kmbl.InventoryManagementService.exceptions.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;

import com.kmbl.InventoryManagementService.models.*;
import org.springframework.http.MediaType;
import com.kmbl.InventoryManagementService.models.Inventory;
import com.kmbl.InventoryManagementService.models.OrderRequestBody;
import com.kmbl.InventoryManagementService.models.ResponseBody;
import com.kmbl.InventoryManagementService.services.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.kmbl.InventoryManagementService.models.Inventory;
import com.kmbl.InventoryManagementService.services.InventoryService;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;


    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    //Get All Inventory Items
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Inventory>> getAllInventoryItems() {
        Iterable<Inventory> inventoryItems = inventoryService.getAllInventoryItems();
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getAllInventoryItemById(@PathVariable("id") String id){
        try {
            Inventory inventoryItems = inventoryService.getInventoryItemById(id);
            return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventoryItem(@RequestBody Inventory inventoryItem) {
        Inventory createdItem = inventoryService.createInventoryItem(inventoryItem);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventoryItem(@PathVariable("id") String id, @RequestBody Inventory updatedItem) throws ResourceNotFoundException {
        try {
            Inventory existingItem = inventoryService.getInventoryItemById(id);
            Inventory savedItem = inventoryService.updateInventoryItem(existingItem.getId(), updatedItem);
            return new ResponseEntity<>(savedItem, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Inventory> deleteInventoryItem(@PathVariable("id") String id){
        try {
            Inventory existingItem = inventoryService.getInventoryItemById(id);
            inventoryService.deleteInventory(existingItem.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping("/updateInventory")
    public ResponseEntity<ResponseBody> updateInventoryforOrderItems(@RequestBody List<OrderRequestBody> orderRequestBodies) {
        ResponseBody responseItems = inventoryService.updateInventoryforOrder(orderRequestBodies);
        if (responseItems == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(responseItems, HttpStatus.OK);
    }
}
