package com.kmbl.InventoryManagementService.repositories;


import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Repository;

import com.kmbl.InventoryManagementService.models.Inventory;


@Repository
@EnableScan
public interface InventoryRepository extends DynamoDBCrudRepository<Inventory, String> {

    
}