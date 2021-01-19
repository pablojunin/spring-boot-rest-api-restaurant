package com.restaurant.demo.jms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.restaurant.demo.model.Sale;
import com.restaurant.demo.service.ISaleService;

@Component
public class Worker {

	private static final String QUEUE_REQUEST = "QUEUE_REQUEST";
	private static final String QUEUE_RESULT = "QUEUE_RESULT";
	@Autowired
	private ISaleService saleService;
	@Autowired
	private JmsTemplate jmsTemplate;

	@JmsListener(destination = QUEUE_REQUEST, containerFactory = "myFactory")
	public void receiveMessage(String msg) {
		System.out.println("Receiver: Received <" + msg + ">");
		//Simulate traffic
		try {
			Thread.sleep(6000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		
		if (msg.equals("getCurrentSales")) {
			try {
				List<Sale> sales = this.saleService.getCurrentSales();
				jmsTemplate.convertAndSend(QUEUE_RESULT, sales);
				System.out.println("Receiver: Sending Result " + sales.toString() );
			} catch (JmsException ex) {
				ex.printStackTrace();
			}
		}


	}

}