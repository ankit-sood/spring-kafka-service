package dev.ankis.consumer;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@EmbeddedKafka
@SpringBootTest(properties = "kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConsumerE2ETest {
    private final String TOPIC_NAME = "spring-kafka-topic1";

    private Producer<Long, String> producer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @SpyBean
    private Consumer consumer;

    @Captor
    ArgumentCaptor<String> messageArgumentCaptor;

    @Captor
    ArgumentCaptor<Acknowledgment> ackArgumentCaptor;

    @Captor
    ArgumentCaptor<Integer> partitionArgumentCaptor;

    @Captor
    ArgumentCaptor<Long> offsetArgumentCaptor;

    @Captor
    ArgumentCaptor<Long> timestampArgumentCaptor;

    @BeforeAll
    void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        producer = new DefaultKafkaProducerFactory<>(configs, new LongSerializer(), new StringSerializer()).createProducer();
    }

    @Test
    void testLogKafkaMessages() throws ExecutionException, InterruptedException {
        String messageValue = "{\"message\":  \"Test Message 15\"}";
        producer.send(new ProducerRecord<>(TOPIC_NAME, 15l, messageValue));
        producer.flush();

        verify(consumer, timeout(5000).times(1))
                .listen(messageArgumentCaptor.capture(), ackArgumentCaptor.capture(),
                partitionArgumentCaptor.capture(), offsetArgumentCaptor.capture(), timestampArgumentCaptor.capture());

        assertNotNull(messageArgumentCaptor.getValue());
    }

    @AfterAll
    void shutdown() {
        producer.close();
    }
}
