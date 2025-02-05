package dev.ankis.producers;

import dev.ankis.SpringKafkaServiceApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class Producer {
    private final KafkaTemplate<Long, String> kafkaTemplate;
    private final String topicName;

    public void send(Long key, String message) {
        this.kafkaTemplate.send(SpringKafkaServiceApplication.TOPIC_NAME, key, message);
    }
}
