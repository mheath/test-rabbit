package io.pivotal.mheath;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestRabbitApplication {

	@Bean
	CachingConnectionFactory factory() throws Exception {
		final CachingConnectionFactory factory = new CachingConnectionFactory();
//		factory.setUri("amqps://cd1c6bb8-491d-4226-97fb-a645e43b4b0d:89qkv63lk0a9ulibs3bpenklst@10.10.38.8:5671/2002fc42-3895-45ef-b364-62a49252ff15");
		factory.setHost("10.10.38.8");
		factory.setPort(5671);
		factory.setUsername("cd1c6bb8-491d-4226-97fb-a645e43b4b0d");
		factory.setPassword("89qkv63lk0a9ulibs3bpenklst");
		factory.setVirtualHost("2002fc42-3895-45ef-b364-62a49252ff15");
//		factory.getRabbitConnectionFactory().useSslProtocol();
		return factory;
	}

	@Bean
	RabbitTemplate template() throws Exception {
		return new RabbitTemplate(factory());
	}

	@RequestMapping("/publish")
	public void publish() throws Exception {
		template().send("spring.cloud.hystrix.stream", "#", new Message("Your mom goes to college".getBytes(), new MessageProperties()));
	}

	public static void main(String[] args) {
		SpringApplication.run(TestRabbitApplication.class, args);
	}
}
