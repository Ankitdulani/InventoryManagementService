package com.kmbl.InventoryManagementService.models.kafka;

public enum KafkaMessagesType {
    PRE_BOOKED("prebook-queue"), ACKNOWLEDGEMENT("acknowledged-queue");

    KafkaMessagesType(String topic){
        kafkaTopic = topic.toLowerCase();
    }

    private String kafkaTopic;

    public String getKafkaTopic() {
        return kafkaTopic;
    }
}
