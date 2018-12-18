package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.dataformat.xstream.XStreamDataFormat;
import org.springframework.stereotype.Component;

import com.example.domain.Customer;
@Component
public class ReadJsonDataFromMQRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		Map<String,String> reference = new HashMap<String,String>();
		reference.put("customer", Customer.class.getName());
		XStreamDataFormat dataformat = new XStreamDataFormat();
		dataformat.setAliases(reference);
		dataformat.setPermissions(Customer.class.getName());
		GsonDataFormat json = new GsonDataFormat(Customer.class);
		
		from("activemq:queue:inputJson")
		.log("the json data is ${body}")
		.unmarshal(json)
		.log("after unmarshal of json is ${body}")
		.marshal(dataformat)
		.log("after marshalling the object is ${body}")
		.to("activemq:queue:outputXml");
	}

}
