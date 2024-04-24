package com.kmbl.InventoryManagementService.models;

import java.util.List;

public class ResponseBody {
    private InventoryOrderStatus inventoryOrderStatus;
    private List<ResponseObject> responseObjects;



    public InventoryOrderStatus getInventoryOrderStatus() {
        return inventoryOrderStatus;
    }

    public void setInventoryOrderStatus(InventoryOrderStatus inventoryOrderStatus) {
        this.inventoryOrderStatus = inventoryOrderStatus;
    }



    public List<ResponseObject> getResponseObjects() {
        return responseObjects;
    }

    public void setResponseObjects(List<ResponseObject> responseObjects) {
        this.responseObjects = responseObjects;

    }

    public void addResponseObject(ResponseObject responseObject) {
        responseObjects.add(responseObject);
    }





}
