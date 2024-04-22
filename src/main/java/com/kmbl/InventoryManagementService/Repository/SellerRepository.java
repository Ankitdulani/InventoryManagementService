package com.kmbl.InventoryManagementService.Repository;

import com.kmbl.InventoryManagementService.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.*;

public interface SellerRepository extends JpaRepository<Seller,Long> {


}
