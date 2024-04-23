package com.kmbl.InventoryManagementService.repositories;

import com.kmbl.InventoryManagementService.models.Seller;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface SellerRepository extends CrudRepository<Seller,String> {


}
