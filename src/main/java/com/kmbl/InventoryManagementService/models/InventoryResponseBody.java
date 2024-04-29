package com.kmbl.InventoryManagementService.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryResponseBody {
    private InventoryOrderStatus inventoryOrderStatus;
    private final List<InventoryResponseObject> inventoryResponseObjects = new ArrayList<>();

    public void addResponseObject(InventoryResponseObject inventoryresponseObject) {

        inventoryResponseObjects.add(inventoryresponseObject);
    }


}
