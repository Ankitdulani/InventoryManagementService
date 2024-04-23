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
@RequestMapping("/Seller")
public class SellerController {

    private final SellerServiceImpl sellerService;

    @Autowired
    public SellerController(SellerServiceImpl sellerService) {

        this.sellerService = sellerService;
    }




    @GetMapping(value = "/{sellerID}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Seller>  getSeller( @PathVariable("sellerID") String sellerID) {

        Seller item= sellerService.getSeller(sellerID);
        if (item==null)
        {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }

             return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }



    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Seller>> getAllSellerItems(){
        List<Seller> sellerItems = sellerService.getAllSellers();
        return new ResponseEntity<>(sellerItems, HttpStatus.OK);


    }

    @PostMapping
    public ResponseEntity<HttpStatus> createSellerDetails(@RequestBody Seller seller) {

        return  new ResponseEntity<>( HttpStatus.CREATED);


    }

    @DeleteMapping("/{sellerID}")
    public ResponseEntity<Seller> deleteSellerDetails(@PathVariable("SellerID") String id) {
        sellerService.deleteSeller(id);

        Seller existingItem = sellerService.getSeller(id);
        if(existingItem==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        sellerService.deleteSeller(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/{SellerID}")
    public ResponseEntity<HttpStatus> updateSeller(@PathVariable("id") String SellerID, @RequestBody Seller seller) {

         sellerService.updateSeller(SellerID, seller);
         return  new ResponseEntity<>( HttpStatus.OK);
    }
}
