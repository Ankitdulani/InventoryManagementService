package com.kmbl.InventoryManagementService.models.kafka;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelOrderMessage implements Message {

    private String orderId;
    private List<OrderItem> orderItems;
    private Long timestamp;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItem{
        private String productId;
        private int quantity;
        private String sellerId;
        private Date date;
    }

    @JsonIgnore
    @Override
    public String getMessage() {
        return this.toString();
    }

    @JsonIgnore
    @Override
    public KafkaMessagesType getType() {
        return KafkaMessagesType.DELETED_ORDER;
    }
}
