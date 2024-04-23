package com.kmbl.InventoryManagementService.models.kafka;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class AcknowledgmentMessage implements Message {

    String orderId;
    String status;

    @Override
    public String getMessage() {
        Map<String ,String> map = new HashMap<>();
        map.put("order_id",orderId);
        map.put("status",status);
        return map.toString();
    }

    @Override
    public KafkaMessagesType getType() {
        return KafkaMessagesType.ACKNOWLEDGEMENT;
    }
}
