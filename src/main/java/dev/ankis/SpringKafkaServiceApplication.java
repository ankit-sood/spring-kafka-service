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
	public static final String TOPIC_NAME = "spring-kafka-topic2";

	@Autowired
	Producer producer;

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaServiceApplication.class, args);
	}

	@Bean
	public NewTopic topic(){
		return TopicBuilder.name(TOPIC_NAME)
				.partitions(2)
				.replicas(1)
				.build();
	}

	@Override
	public void run(String... args) throws Exception {
		//producer.send("14", "{\"message\":  \"Test Message 14\"}", TOPIC_NAME);
	}
}
