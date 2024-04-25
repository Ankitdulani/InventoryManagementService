package com.kmbl.InventoryManagementService.models;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryOrderItem {
    private String productId;
    private String sellerId;
    private String quantity;
}
