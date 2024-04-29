package com.kmbl.InventoryManagementService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kmbl.InventoryManagementService.exceptions.ServiceException;
import com.kmbl.InventoryManagementService.models.kafka.Message;
import com.kmbl.InventoryManagementService.models.kafka.CancelOrderMessage;
import com.kmbl.InventoryManagementService.models.kafka.PaymentMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.kmbl.InventoryManagementService.exceptions.KafkaProcessingException.KafkaException.INCORRECT_FORMAT;
import static com.kmbl.InventoryManagementService.exceptions.KafkaProcessingException.KafkaException.MISSING_ORDER_ITEM;

@Slf4j
@Component
public class KafkaMessagingService implements MessagingService{

    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper;
    private InventoryService inventoryService;

    private final String errorLogFormat = " DATE: %s - TYPE : { %s }";
    private final String errorLogFormatWithMessage = " DATE: %s - TYPE : { %s } - Message : { %s }";

    @Autowired
    public KafkaMessagingService(KafkaTemplate<String, String> template, ObjectMapper mapper,InventoryService iService){
        kafkaTemplate = template;
        objectMapper = mapper;
        inventoryService = iService;
    }

    @Override
    public void publish(Message message) {
        // handle exceptions here
        kafkaTemplate.send(message.getType().getKafkaTopic(),message.getMessage());
    }

    /*
    TODO:
        adding multiple consumer
        making consumer idempotent
        partition of data
    */
    @KafkaListener(topics = "delete-order-queue",
                    groupId = "consumerGroup-" + "#{T(java.util.UUID).randomUUID()}",
                    autoStartup = "true")
    public void orderDelete(@Payload String kafkaMessage)  {

        try {
            log.info("Message received: {}", kafkaMessage);
            CancelOrderMessage message = objectMapper.readValue(kafkaMessage, CancelOrderMessage.class);
            inventoryService.freeInventory(message);

        }catch ( JsonProcessingException   ex){
            log.error(String.format(errorLogFormat,new Date().toString(), INCORRECT_FORMAT),ex);
        }catch (ServiceException ex){
            log.error(String.format(errorLogFormatWithMessage,new Date().toString(), MISSING_ORDER_ITEM,ex.getMessage() ),ex);
        }
    }
}
