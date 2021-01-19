package com.restaurant.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.restaurant.demo.RestaurantDemoApplication;
import com.restaurant.demo.dto.AuthenticateDTO;
import com.restaurant.demo.model.Sale;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = RestaurantDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SaleRestControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@LocalServerPort
	private int port;

	private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZXN0YXVyYW50IiwiZXhwIjoxNjExMDc4NTM3LCJpYXQiOjE2MTEwNjA1Mzd9.q1N7lrOBuUbTdt6ZeuDsSI9-vru1d7mU7FqksnNkXs4D89i4eG9UydDiSgJ0NE_MYD4-d2DuHZlacXY-LtuheQ";


	private String getRootUrl() {
		return "http://localhost:" + port;
	}	
	
	@Test
	void testCreateSale() {
		String requestJson = "{\"description\": \"My new Sale\",\"billNumber\": 23456,\"amount\": 200,\"itemsQuantity\": 23, \"date\": \"2021-01-18\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer "+ token);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		ResponseEntity<AuthenticateDTO> response = restTemplate.exchange(getRootUrl() + "/restaurant/api/v1/sales", HttpMethod.POST, entity, AuthenticateDTO.class);
		
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);	


		HttpHeaders headers2 = new HttpHeaders();
		headers2.setContentType(MediaType.APPLICATION_JSON);
		headers2.set("Authorization", "Bearer "+ token);
		HttpEntity<String> entity2 = new HttpEntity<String>(null, headers2);
		
		ResponseEntity<ArrayList> response2 = restTemplate.exchange(getRootUrl() + "/restaurant/api/v1/sales",
				HttpMethod.GET, entity2, ArrayList.class);
		
		Boolean newOk = false;
		List<Sale> list = new ArrayList();
		for (Object i : response2.getBody()) {
			Sale saleTemp = modelMapper.map(i, Sale.class);
			newOk = (saleTemp.getDescription().equals("My new Sale"))? true: newOk;
			list.add(saleTemp);
		}
		
		Assertions.assertNotNull(response2.getBody());
		Assertions.assertEquals(response2.getStatusCode(), HttpStatus.OK);	
		Assertions.assertTrue(newOk);	
		
	}

}
