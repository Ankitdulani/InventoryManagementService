package com.kmbl.InventoryManagementService.services;

import com.kmbl.InventoryManagementService.models.kafka.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessagingService implements MessagingService{

    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaMessagingService(KafkaTemplate<String, String> template){
        kafkaTemplate = template;
    }

    @Override
    public void publish(Message message) {
        // handle exceptions here
        kafkaTemplate.send(message.getType().getKafkaTopic(),message.getMessage());
    }

    @KafkaListener(topics = "prebooking" ,
                    groupId = "consumerGroup-" + "#{T(java.util.UUID).randomUUID()}",
                    autoStartup = "true")
    public void listenWithHeaders(@Payload String message) {

        //handle exception, adding multiple consumer, making consumer idempotent
        // consumer group acknowledgment
        System.out.println(  "Received Message: " + message);
    }


}
