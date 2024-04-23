package com.kmbl.InventoryManagementService.service;

import com.kmbl.InventoryManagementService.models.Seller;

import java.util.List;

public interface SellerServiceInterface {

    public void createSeller(Seller seller);
    public void updateSeller(String sellerID,Seller seller);
    public  void deleteSeller(String sellerID);
    public Seller getSeller(String sellerID);
    public List<Seller> getAllSellers();
}
