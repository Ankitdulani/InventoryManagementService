package com.kmbl.InventoryManagementService.Controllers;

import com.kmbl.InventoryManagementService.models.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Seller")
public class SellerApiService {

    @Autowired
    Seller seller;

    @GetMapping("{sellerID}")
    public Seller getSeller(String sellerID )
    {
        return seller;
    }

    @PostMapping
    public String createSellerDetails(@RequestBody Seller seller)
    {
        this.seller=seller;
        return "Seller created auccessfully";
    }

    @DeleteMapping("/{sellerID}")
    public String deleteSellerDetails(@PathVariable("SellerID") String id)
    {
        return  "Seller Deleted";
    }

    @PutMapping("/Seller/{id}")
    public void updateSeller(@PathVariable("id")String id,Seller seller)
    {
            //To-do
    }
}
