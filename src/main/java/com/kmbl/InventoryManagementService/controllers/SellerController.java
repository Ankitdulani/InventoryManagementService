package com.kmbl.InventoryManagementService.controllers;

import com.kmbl.InventoryManagementService.models.Seller;
import com.kmbl.InventoryManagementService.services.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final SellerServiceImpl sellerService;

    @Autowired
    public SellerController(SellerServiceImpl sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping(value = "/{sellerID}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Seller> getSeller( @PathVariable("sellerID") String sellerID) {
        try {
            Seller item = sellerService.getSeller(sellerID);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Seller>> getAllSellerItems(){
        List<Seller> sellerItems = sellerService.getAllSellers();
        return new ResponseEntity<>(sellerItems, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Seller> createSellerDetails(@RequestBody Seller seller) {
        Seller createdSeller = sellerService.createSeller(seller);
        return new ResponseEntity<>(createdSeller, HttpStatus.CREATED);
    }

    @DeleteMapping("/{sellerID}")
    public ResponseEntity<Seller> deleteSellerDetails(@PathVariable("sellerID") String sellerID) {
        try {
            sellerService.deleteSeller(sellerID);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{sellerID}")
    public ResponseEntity<Seller> updateSeller(@PathVariable("sellerID") String sellerID, @RequestBody Seller seller) {
         try {
             Seller updatedSeller = sellerService.updateSeller(sellerID, seller);
             return new ResponseEntity<>(updatedSeller, HttpStatus.OK);
         }  catch (Exception exception) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
    }
}
