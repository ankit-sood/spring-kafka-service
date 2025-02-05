package dev.ankis.configuration;

import dev.ankis.producers.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({KafkaProducerProperties.class})
public class ProducerConfiguration {

    @Bean
    public Producer sender(KafkaTemplate<Long, String> template, KafkaProducerProperties producerProperties) {
        return new Producer(template, producerProperties.topicName());
    }

    @Bean
    public KafkaTemplate<Long, String> kafkaTemplate(ProducerFactory<Long, String> producerFactory,
                                                     ProducerListener<Long, String> producerListener) {
        KafkaTemplate<Long, String> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setProducerListener(producerListener);
        return kafkaTemplate;
    }

    @Bean
    public ProducerFactory<Long, String> producerFactory(KafkaProducerProperties producerProperties) throws ClassNotFoundException {
        return new DefaultKafkaProducerFactory<>(senderProps(producerProperties));
    }

    private Map<String, Object> senderProps(KafkaProducerProperties producerProperties) throws ClassNotFoundException {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerProperties.bootstrapServers());
        props.put(ProducerConfig.LINGER_MS_CONFIG, producerProperties.lingerMs());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Class.forName(producerProperties.keySerializer()));
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Class.forName(producerProperties.valueSerializer()));
        return props;
    }
}
