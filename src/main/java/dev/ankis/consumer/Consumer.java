package dev.ankis.consumer;

import dev.ankis.configuration.KafkaConsumerProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class Consumer implements ConsumerSeekAware {
    public CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "${kafka.consumer.topic-name}")
    public void listen(@Payload String data,
                       Acknowledgment ack,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                       @Header(KafkaHeaders.OFFSET) long offset,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp){
        try{
            log.info("Cons Partition {}, Offset {}, Timestamp {}, Data {}", partition, offset, timestamp, data);
            latch.countDown();
        } finally {
            ack.acknowledge();
        }
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        //callback.seekToTimestamp(assignments.keySet(), 1735195843000l);
    }
}
