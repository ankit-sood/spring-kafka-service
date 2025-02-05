package dev.ankis;

import dev.ankis.producers.Producer;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class SpringKafkaServiceApplication implements CommandLineRunner {
	public static final String TOPIC_NAME = "spring-kafka-topic3";

	@Autowired
	Producer producer;

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaServiceApplication.class, args);
	}

	@Bean
	public NewTopic topic(){
		return TopicBuilder.name(TOPIC_NAME)
				.partitions(1)
				.replicas(1)
				.build();
	}

	@Override
	public void run(String... args) throws Exception {
//		producer.send("17", "{\"message\":  \"Test Message 12\"}", TOPIC_NAME);
//		producer.send("18", "{\"message\":  \"Test Message 13\"}", TOPIC_NAME);
//		producer.send("19", "{\"message\":  \"Test Message 14\"}", TOPIC_NAME);
//		producer.send("20", "{\"message\":  \"Test Message 15\"}", TOPIC_NAME);
//		producer.send("21", "{\"message\":  \"Test Message 16\"}", TOPIC_NAME);

	}
}
