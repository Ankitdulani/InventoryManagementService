package com.kmbl.InventoryManagementService.controllers;

import com.kmbl.InventoryManagementService.models.Seller;
import com.kmbl.InventoryManagementService.service.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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




    @GetMapping(value = "{sellerID}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Seller getSeller(String sellerID) {

        return sellerService.getSeller(sellerID);
    }

    @GetMapping
    public List<Seller> getAllSellerItems(){
        List<Seller> sellerItems = sellerService.getAllSellers();
        return sellerItems;

    }

    @PostMapping
    public String createSellerDetails(@RequestBody Seller seller) {

        sellerService.createSeller(seller);
        return "Seller created auccessfully!";
    }

    @DeleteMapping("/{sellerID}")
    public String deleteSellerDetails(@PathVariable("SellerID") String id) {
        sellerService.deleteSeller(id);
        return "Seller Deleted!";
    }

    @PutMapping("/Seller/{id}")
    public String updateSeller(@PathVariable("id") String id, Seller seller) {

        return sellerService.updateSeller(id, seller);
    }
}
