package com.kmbl.InventoryManagementService.Service;

import com.kmbl.InventoryManagementService.models.Seller;

import java.util.List;

public interface SellerServiceInterface {

    public String createSeller(Seller seller);
    public String updateSeller(Seller seller);
    public  String deleteSeller(String sellerID);
    public Seller getSeller(String sellerID);
    public List<Seller> getAllsellers();
}
