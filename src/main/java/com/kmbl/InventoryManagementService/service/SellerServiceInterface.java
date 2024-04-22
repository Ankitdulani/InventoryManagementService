package com.kmbl.InventoryManagementService.service;

import com.kmbl.InventoryManagementService.models.Seller;

import java.util.List;

public interface SellerServiceInterface {

    public String createSeller(Seller seller);
    public String updateSeller(String sellerID,Seller seller);
    public  String deleteSeller(String sellerID);
    public Seller getSeller(String sellerID);
    public List<Seller> getAllSellers();
}
