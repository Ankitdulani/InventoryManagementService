package com.kmbl.InventoryManagementService.services;

import com.kmbl.InventoryManagementService.exceptions.ResourceNotFoundException;
import com.kmbl.InventoryManagementService.models.Seller;

import java.util.List;

public interface SellerServiceInterface {

    public Seller createSeller(Seller seller);
    public Seller updateSeller(String sellerID, Seller seller) throws ResourceNotFoundException;
    public  void deleteSeller(String sellerID);
    public Seller getSeller(String sellerID) throws ResourceNotFoundException;
    public List<Seller> getAllSellers();
}
