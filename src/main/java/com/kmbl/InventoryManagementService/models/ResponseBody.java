package com.kmbl.InventoryManagementService.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseBody {
    private InventoryOrderStatus inventoryOrderStatus;
    private final List<ResponseObject> responseObjects = new ArrayList<>();

    public void addResponseObject(ResponseObject responseObject) {

        responseObjects.add(responseObject);
    }


}
