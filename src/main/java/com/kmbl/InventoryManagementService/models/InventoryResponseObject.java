package com.kmbl.InventoryManagementService.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryResponseObject {
    private String orderItemStatus;
    private InventoryOrderItem data;
    private int fullfillCount;
}
