package dev.ankis.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kafka.producer")
public record KafkaProducerProperties(String bootstrapServers,
                                      String keySerializer,
                                      String valueSerializer,
                                      String topicName,
                                      Integer lingerMs) {
}
