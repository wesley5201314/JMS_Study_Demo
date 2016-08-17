package com.activemq.demo;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestContrller {

	@Autowired  
    @Qualifier("queueDestination")  
    private Destination destination;  
	
	@Autowired
	private TemplateProducer templateProducer;
	
	@RequestMapping("/sendMessage")
	public void find(){
		templateProducer.sendMessage(destination, "看电影~");
	}
}
