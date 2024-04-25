package com.kmbl.InventoryManagementService.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseObject {
    private String status;
    private InventoryOrderItem data;
    private int count;
}
