package com.kmbl.InventoryManagementService.models.kafka;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreBookingMessage implements Message {

    private String orderId;
    private List<OrderItem> orderItems;
    private Long timestamp;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    class OrderItem{
        private String productId;
        private String quantity;
        private String sellerId;
        private Date date;
    }

    @Override
    public String getMessage() {
        return this.toString();
    }

    @Override
    public KafkaMessagesType getType() {
        return KafkaMessagesType.PRE_BOOKED;
    }
}
