package com.kmbl.InventoryManagementService.services;

import com.kmbl.InventoryManagementService.models.Seller;
import com.kmbl.InventoryManagementService.repositories.SellerRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SellerServiceImpl implements  SellerServiceInterface{

    private static final Logger logger = LogManager.getLogger(InventoryService.class);
    private SellerRepository sellerRepository;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository) {

        this.sellerRepository = sellerRepository;
    }


    @Override
    public void createSeller(Seller seller) {
        logger.info("Creating New Seller");
        sellerRepository.save(seller);

    }

    @Override
    public void updateSeller(String sellerID,Seller seller) {
        logger.info("Updating Seller");
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
        logger.info("Fetching Seller");
        return sellerRepository.findById(sellerID).orElse(null);
    }

    @Override
    public List<Seller> getAllSellers() {
        return (List<Seller>) sellerRepository.findAll();
    }
}
