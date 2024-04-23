package com.kmbl.InventoryManagementService.services;

import com.kmbl.InventoryManagementService.models.kafka.Message;

public interface MessagingService {
    public void publish(Message message);
}
