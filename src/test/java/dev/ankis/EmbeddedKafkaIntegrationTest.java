package dev.ankis;

import dev.ankis.consumer.Consumer;
import dev.ankis.producers.Producer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class EmbeddedKafkaIntegrationTest {
    @Autowired
    private Producer producer;

    @Autowired
    private Consumer consumer;

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived()
            throws Exception {
        String messageValue = "{\"message\":  \"Test Message 15\"}";
        producer.send(15l, messageValue);

        boolean messageConsumed = consumer.latch.await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        //assertThat(consumer.getPayload(), containsString(data));
    }
}
