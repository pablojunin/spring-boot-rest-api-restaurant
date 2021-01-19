package com.restaurant.demo;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.restaurant.demo.dao.ISaleRepository;
import com.restaurant.demo.model.Sale;


@Component
public class SaleDbInit implements CommandLineRunner {
	
	@Autowired
	private ISaleRepository repository;

	@Override
	public void run(String... args) throws Exception {
		Sale sal = new Sale(0l,"A litle description", LocalDate.now(), 23456, 200.0f, 23);		
		this.repository.save(sal);
	}
}
