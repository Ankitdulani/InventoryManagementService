package com.kmbl.InventoryManagementService.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import com.kmbl.InventoryManagementService.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmbl.InventoryManagementService.repositories.InventoryRepository;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;
    }

    //Get All Inventory Items
    public Iterable<Inventory> getAllInventoryItems(){
        return inventoryRepository.findAll(); 
    }

    //Get inventory item by ID
    public Optional<Inventory> getInventoryItemById(String id){
        return inventoryRepository.findById(id);
    }

    //create a new inventory
    public Inventory createInventoryItem(Inventory inventory){
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventoryItem(String id, Inventory updatedInventory){

        Inventory existingInventory=inventoryRepository.findById(id).orElse(null);
        if(existingInventory!=null)
        {
            return null;
        }
        existingInventory=updatedInventory;

        return inventoryRepository.save(existingInventory);
    }

    public void deleteInventory(String id){
        inventoryRepository.deleteById(id);
    }

    public ResponseBody  updateInventoryforOrder(List<OrderRequestBody> orderRequestItems)
    {
        //get list of inventory
           synchronized (this) {
                InventoryOrderStatus overallStatus=InventoryOrderStatus.FAILED_ORDER;
              //  List<ResponseObject> tempResponse=new ArrayList<>();
                ResponseBody response = new ResponseBody();
                for(int i=0;i<orderRequestItems.size();i++) {

                    ResponseObject responseObject=new ResponseObject();
                    List<Inventory> tempInventory = inventoryRepository.findByproductId(orderRequestItems.get(i).getProductID());
                    //, orderRequestItems.get(i).getSellerID());
                    Inventory inventoryFinal=null;
                    for(int j=0;j<tempInventory.size();j++)
                    {
                        if(tempInventory.get(j).getSellerId()==orderRequestItems.get(j).getSellerID())
                        {
                            inventoryFinal=tempInventory.get(j);
                        }
                    }
                    Integer tempCount = 0;


                    InventoryOrderStatus tempStatus = InventoryOrderStatus.FAILED_ORDER;


                    if (inventoryFinal==null || inventoryFinal.getQuantity() == 0) {
                        tempCount = 0;
                        tempStatus = InventoryOrderStatus.FAILED_ORDER;
                    } else {

                      //create response
                        overallStatus=InventoryOrderStatus.PARTIAL_ORDER;
                        int orderCountNeeded=orderRequestItems.get(i).getCount();
                        tempCount = (inventoryFinal.getQuantity() < orderCountNeeded) ? inventoryFinal.getQuantity() : orderCountNeeded;
                        tempStatus = (tempCount == orderRequestItems.get(i).getCount()) ? InventoryOrderStatus.COMPLETE_ORDER : InventoryOrderStatus.PARTIAL_ORDER;
                        //update the changes
                        int inventoryUpdatedCount=inventoryFinal.getQuantity()-orderCountNeeded;
                        int newQuantity = orderCountNeeded < inventoryFinal.getQuantity() ?inventoryUpdatedCount:0;
                        inventoryFinal.setQuantity(newQuantity);
                        inventoryRepository.save(inventoryFinal);
                    }
                    //create orderitem for responseobject
                        OrderItem tempOrderItem=new OrderItem();
                        tempOrderItem.setProductId(orderRequestItems.get(i).getProductID());
                        tempOrderItem.setSellerId(orderRequestItems.get(i).getSellerID());
                        String orderItemQuantity=String.valueOf(orderRequestItems.get(i).getCount());
                        tempOrderItem.setQuantity(orderItemQuantity);

                        responseObject.setData(tempOrderItem);
                        responseObject.setStatus(String.valueOf(tempStatus));
                        responseObject.setCount(tempCount);


                    response.addResponseObject(responseObject);

                }
                    //set responsebody status
                        response.setInventoryOrderStatus(overallStatus);

                return response;
            }

    }
}
