package dev.ankis.producers;

import dev.ankis.SpringKafkaServiceApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@RequiredArgsConstructor
public class SyncProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topicName;

    public void send(String key, String message, String topicName) {
        try {
            SendResult<String, String> result = kafkaTemplate.send(SpringKafkaServiceApplication.TOPIC_NAME, key, message).get(10, TimeUnit.SECONDS);
            log.info("Sent message to {}", result.getRecordMetadata());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}

