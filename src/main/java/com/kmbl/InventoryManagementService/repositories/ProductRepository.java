package com.kmbl.InventoryManagementService.repositories;

import com.kmbl.InventoryManagementService.models.Product;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

@EnableScan
public interface ProductRepository extends DynamoDBCrudRepository<Product, String> {

}
