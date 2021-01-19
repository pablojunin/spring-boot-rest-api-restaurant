package com.restaurant.demo.controller;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.restaurant.demo.RestaurantDemoApplication;
import com.restaurant.demo.dao.ISaleRepository;
import com.restaurant.demo.dto.SaleDTO;
import com.restaurant.demo.dto.AuthenticateDTO;
import com.restaurant.demo.model.Sale;
import com.restaurant.demo.service.SaleServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = RestaurantDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SaleRestControllerTest2 {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@MockBean
    private SaleServiceImpl service;
	
	@Autowired
    private ISaleRepository repository;
	
	@LocalServerPort
	private int port;

	private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZXN0YXVyYW50IiwiZXhwIjoxNjExMDg2ODgxLCJpYXQiOjE2MTEwNjg4ODF9.d9vUZEGOLUj4X2gtAw075s2uA1rWjt3IPqxSfZZDiRyNv2sNUDvIzKcW4MovwOWZPXBH4QeXkKx07k-Fl1RNjA";

	private ArrayList<Sale> listAllSales;
	
	private ArrayList<Sale> listCurrentSales;
	
	private String getRootUrl() {
		return "http://localhost:" + port;
	}
	
    @BeforeAll
    public static void init(){

    }
    
	@BeforeEach
	public void contextLoads() {
		LocalDate today = LocalDate.now();
	    LocalDate yesterday = today.minusDays( 1 );
    	Sale sale1 = new Sale(0l,"First sale", today, 1234, 100.0f, 2);  
    	Sale sale2 = new Sale(0l,"Second sale", yesterday, 1234, 100.0f, 2); 
    	listAllSales = new ArrayList<Sale>();
    	listAllSales.add(sale1);
    	listAllSales.add(sale2);
    	
    	listCurrentSales = new ArrayList<Sale>();
    	listCurrentSales.add(sale1);
		
		String requestJson = "{\"username\":\"restaurant\",\"password\":\"password\"}";	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		ResponseEntity<AuthenticateDTO> response = restTemplate.exchange(getRootUrl() + "/restaurant/authenticate", HttpMethod.POST, entity, AuthenticateDTO.class);
		
		token = response.getBody().getToken();
	}

	@Test
	public void testAuthenticate() {
		String requestJson = "{\"username\":\"restaurant\",\"password\":\"password\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer "+ token);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		ResponseEntity<AuthenticateDTO> response = restTemplate.exchange(getRootUrl() + "/restaurant/authenticate", HttpMethod.POST, entity, AuthenticateDTO.class);
		
		String token = response.getBody().getToken();
		
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);	
		
	}
	
	@Test
	public void testGetAllUsersWithFORBIDDEN() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/restaurant/api/v1/sales",
				HttpMethod.GET, entity, String.class);

		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);		
	}
	
	@Test
	public void testGetAllSales() {

    	when(service.getAllSales()).thenReturn(listAllSales);    	
    	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer "+ token);
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<ArrayList> response = restTemplate.exchange(getRootUrl() + "/restaurant/api/v1/sales",
				HttpMethod.GET, entity, ArrayList.class);
		
		List<Sale> list = new ArrayList();
		for (Object i : response.getBody()) {
			Sale saleTemp = modelMapper.map(i, Sale.class);
			list.add(saleTemp);
		}
		
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);	
		Assertions.assertEquals(2, list.size());
		Assertions.assertEquals("First sale", list.get(0).getDescription());
		Assertions.assertEquals("Second sale", list.get(1).getDescription());
		
	}
	
	@Test
	void testGetCurrentSales() {
		
    	when(service.getCurrentSales()).thenReturn(listCurrentSales);    	
    	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer "+ token);
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<ArrayList> response = restTemplate.exchange(getRootUrl() + "/restaurant/api/v1/sales/current",
				HttpMethod.GET, entity, ArrayList.class);
		
		List<Sale> list = new ArrayList();
		for (Object i : response.getBody()) {
			Sale saleTemp = modelMapper.map(i, Sale.class);
			list.add(saleTemp);
		}
		
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);	
		Assertions.assertEquals(1, list.size());
		Assertions.assertEquals("First sale", list.get(0).getDescription());	
	}

}
