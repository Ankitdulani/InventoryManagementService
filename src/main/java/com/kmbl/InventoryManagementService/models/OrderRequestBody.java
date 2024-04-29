package com.kmbl.InventoryManagementService.models;

import lombok.Data;

@Data

public class OrderRequestBody {
    private String productID;
    private String sellerID;
    private Integer count;
}
