package com.kmbl.InventoryManagementService.models;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
public class Inventory {

    private String id;
    private Seller seller;
    private Product product;
    private int quantity;
    private double price;
    List<Integer> pincodes;
}
