package com.kmbl.InventoryManagementService.service;

import com.kmbl.InventoryManagementService.models.Seller;
import com.kmbl.InventoryManagementService.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SellerServiceImpl implements  SellerServiceInterface{


    private SellerRepository sellerRepository;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository) {

        this.sellerRepository = sellerRepository;
    }


    @Override
    public String createSeller(Seller seller) {
        sellerRepository.save(seller);
        return "saved!";
    }

    @Override
    public String updateSeller(String sellerID,Seller seller) {
        Seller existingSeller = sellerRepository.findById(sellerID).orElse(null);
        if(existingSeller == null){
            
            return "seller doesn't exist!";
        }

        existingSeller=seller;
       sellerRepository.save(existingSeller);
        return "updated!";
    }

    @Override
    public String deleteSeller(String sellerID) {
         sellerRepository.deleteById(sellerID);
         return "deleted!";
    }

    @Override
    public Seller getSeller(String sellerID) {
        return sellerRepository.findById(sellerID).orElse(null);
    }

    @Override
    public List<Seller> getAllSellers() {
        return (List<Seller>) sellerRepository.findAll();
    }
}
