package dev.ankis.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kafka.consumer")
public record KafkaConsumerProperties(String bootstrapServers,
                                      String keyDeserializer,
                                      String valueDeserializer,
                                      String groupId,
                                      String topicName,
                                      String autoOffsetReset,
                                      Integer concurrency) {
}
