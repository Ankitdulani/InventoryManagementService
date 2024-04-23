package com.kmbl.InventoryManagementService.controllers;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    
    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    //Get All Inventory Items
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Inventory>> getAllInventoryItems(){
        Iterable<Inventory> inventoryItems = inventoryService.getAllInventoryItems();
        return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getAllInventoryItemById(@PathVariable("id") String id){
        Optional<Inventory> inventoryItems = inventoryService.getInventoryItemById(id);
        return inventoryItems.map(item -> new ResponseEntity<>(item, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventoryItem(@RequestBody Inventory inventoryItem){
        Inventory createdItem = inventoryService.createInventoryItem(inventoryItem);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventoryItem(@PathVariable("id") String id, @RequestBody Inventory updatedItem){
        Optional<Inventory> existingItem = inventoryService.getInventoryItemById(id);
        if(existingItem.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Inventory savedItem = inventoryService.updateInventoryItem(id, updatedItem);
        return new ResponseEntity<>(savedItem, HttpStatus.OK);
       
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Inventory> deleteInventoryItem(@PathVariable("id") String id){
        Optional<Inventory> existingItem = inventoryService.getInventoryItemById(id);
        if(existingItem.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        inventoryService.deleteInventory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       
    }
}
