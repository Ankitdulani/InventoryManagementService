package com.kmbl.InventoryManagementService.Service;

import com.kmbl.InventoryManagementService.models.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SellerServiceImpl implements  SellerServiceInterface{


    @Autowired
    Seller seller;
    @Override
    public String createSeller(Seller seller) {
        return null;
    }

    @Override
    public String updateSeller(Seller seller) {
        return null;
    }

    @Override
    public String deleteSeller(String sellerID) {
        return null;
    }

    @Override
    public Seller getSeller(String sellerID) {
        return null;
    }

    @Override
    public List<Seller> getAllsellers() {
        return List.of();
    }
}
