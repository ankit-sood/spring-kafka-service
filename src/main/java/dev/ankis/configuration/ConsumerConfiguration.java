package dev.ankis.configuration;

import dev.ankis.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@EnableConfigurationProperties({KafkaConsumerProperties.class})
public class ConsumerConfiguration {
    @Bean
    public Consumer consumer() {
        return new Consumer();
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<Long, String> kafkaListenerContainerFactory(ConsumerFactory<Long, String> consumerFactory,
                                                                                        KafkaConsumerProperties kafkaConsumerProperties) {
        ConcurrentKafkaListenerContainerFactory<Long, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(kafkaConsumerProperties.concurrency());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    @Bean
    public ConsumerFactory<Long, String> consumerFactory(KafkaConsumerProperties kafkaConsumerProperties) throws ClassNotFoundException {
        return new DefaultKafkaConsumerFactory<>(consumerProps(kafkaConsumerProperties));
    }

    private Map<String, Object> consumerProps(KafkaConsumerProperties kafkaConsumerProperties) throws ClassNotFoundException {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerProperties.bootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerProperties.groupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Class.forName(kafkaConsumerProperties.keyDeserializer()));
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Class.forName(kafkaConsumerProperties.valueDeserializer()));
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerProperties.autoOffsetReset());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return props;
    }
}
