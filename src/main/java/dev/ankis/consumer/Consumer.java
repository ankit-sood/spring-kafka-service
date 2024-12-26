package dev.ankis.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public class Consumer {
    @KafkaListener(topics = "${kafka.consumer.topic-name}")
    public void listen(@Payload String data,
                       Acknowledgment ack,

                       @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                       @Header(KafkaHeaders.OFFSET) int offset,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp){
        try{
            System.out.println(data);
        } finally {
            //ack.acknowledge();
        }
    }
}
