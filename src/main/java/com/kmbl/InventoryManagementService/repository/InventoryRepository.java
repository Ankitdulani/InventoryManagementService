package com.kmbl.InventoryManagementService.repository;


@EnableScan
public interface InventoryRepository extends DynamoDBRepository <Inventory, String> {
    
}
