package com.kmbl.InventoryManagementService.repositories;


import com.kmbl.InventoryManagementService.models.Inventory;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Repository;

import com.kmbl.InventoryManagementService.models.Inventory;

import java.util.Optional;
import java.util.List;
import java.util.Optional;


@Repository
@EnableScan
public interface InventoryRepository extends DynamoDBCrudRepository<Inventory, String> {

    List<Inventory> findByproductId(String productId);
    Optional<Inventory> findBySellerIdAndProductId(String sellerId, String productId);

    Optional<Inventory> findBySellerIdAndProductId(String sellerId, String productId);

}
