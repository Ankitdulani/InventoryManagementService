package com.kmbl.InventoryManagementService.Controllers;

import com.kmbl.InventoryManagementService.models.Seller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Seller")
public class SellerApiService {

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
}
