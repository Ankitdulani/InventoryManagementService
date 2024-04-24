package com.kmbl.InventoryManagementService.services;

import com.kmbl.InventoryManagementService.exceptions.ResourceNotFoundException;
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
    public Seller createSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSeller(String sellerID, Seller seller) throws ResourceNotFoundException {
        Seller existingSeller = sellerRepository.findById(sellerID).orElse(null);
        if(existingSeller == null){
            throw new ResourceNotFoundException("Seller Id : {} is not present");
        }
        return sellerRepository.save(seller);
    }

    @Override
    public void deleteSeller(String sellerID) {
         sellerRepository.deleteById(sellerID);
    }

    @Override
    public Seller getSeller(String sellerID) throws ResourceNotFoundException {
        Seller seller = sellerRepository.findById(sellerID).orElse(null);
        if(seller == null) {
            throw new ResourceNotFoundException("Seller Id : {} is not present");
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers() {
        return (List<Seller>) sellerRepository.findAll();
    }
}
