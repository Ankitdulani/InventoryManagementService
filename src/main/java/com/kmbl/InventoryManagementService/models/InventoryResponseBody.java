package com.kmbl.InventoryManagementService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryResponseBody {
    private InventoryOrderStatus orderStatus;
    private final List<InventoryResponseObject> orderItemStatus = new ArrayList<>();

    @JsonIgnore
    public void addResponseObject(InventoryResponseObject inventoryresponseObject) {
        orderItemStatus.add(inventoryresponseObject);
    }
}
