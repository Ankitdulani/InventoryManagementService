package com.kmbl.InventoryManagementService.models;



import java.util.Date;

public class OrderItem {




    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }



    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }



    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    private String sellerId;

    private Date createdAt;

    private Date updatedAt;

    private String orderItemId;

    private String productId;

    private String quantity;

    private OrderStatus status;
}
