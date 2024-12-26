package dev.ankis.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProducerListenerImpl implements ProducerListener<Long, String> {
    @Override
    public void onSuccess(ProducerRecord<Long, String> producerRecord, RecordMetadata recordMetadata) {
        log.info("Key: {} produced at {}",producerRecord.key(), recordMetadata.offset());
    }

    @Override
    public void onError(ProducerRecord<Long, String> producerRecord, RecordMetadata recordMetadata, Exception exception) {
        log.error("Exp: {}", exception.getCause());
    }
}
