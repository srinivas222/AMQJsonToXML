package com.example.demo;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmqJsonToXmlApplication {

	public static void main(String[] args) throws Exception {
		ReadJsonDataFromMQRouter router = new ReadJsonDataFromMQRouter();
		SpringApplication.run(AmqJsonToXmlApplication.class, args);
		CamelContext ctx = new DefaultCamelContext();
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");
		ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(factory));
		ctx.addRoutes(router);
		ctx.start();
		Thread.sleep(5*60*1000);
		ctx.stop();
	}

}

