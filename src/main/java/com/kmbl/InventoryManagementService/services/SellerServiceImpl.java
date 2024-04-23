package com.kmbl.InventoryManagementService.services;

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
    public void createSeller(Seller seller) {
        sellerRepository.save(seller);

    }

    @Override
    public void updateSeller(String sellerID,Seller seller) {
        Seller existingSeller = sellerRepository.findById(sellerID).orElse(null);
        if(existingSeller == null){

            return ;
        }

        existingSeller=seller;
       sellerRepository.save(existingSeller);

    }

    @Override
    public void deleteSeller(String sellerID) {
         sellerRepository.deleteById(sellerID);

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
