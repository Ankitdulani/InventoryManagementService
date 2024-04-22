package com.kmbl.InventoryManagementService.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Seller {
    private String id;
    private String name;
    private String address;
    private double  rating;
}
