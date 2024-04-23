package com.kmbl.InventoryManagementService.models.kafka;

public interface Message {

    public String getMessage();
    public KafkaMessagesType getType();
}
