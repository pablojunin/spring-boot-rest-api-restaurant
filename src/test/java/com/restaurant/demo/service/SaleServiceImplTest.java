package com.restaurant.demo.service;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.restaurant.demo.dao.ISaleRepository;
import com.restaurant.demo.model.Sale;


@ExtendWith(MockitoExtension.class)
class SaleServiceImplTest {
 
    /**
     * Sale Repository
     */
    @Mock
    private ISaleRepository repository;
    
    /**
     * Sale Service
     */
	@InjectMocks
    private SaleServiceImpl service;
    
    @Test
    @DisplayName("Create Sale")
	void testCreateSale() {
    	Sale sale1 = new Sale(0l,"First sale ", LocalDate.now(), 1234, 100.0f, 2);
    	
    	when(repository.save(sale1)).thenReturn(sale1);
    	
    	Sale result = service.createSale(sale1);
    	
    	Assertions.assertEquals(result, sale1, "getAllSales should return 2 sales");
	}

    @Test
    @DisplayName("Get all sales")
	void testGetAllSales() {
		LocalDate today = LocalDate.now();
	    LocalDate yesterday = today.minusDays( 1 );
        // Setup our mock repository
        Sale sale1 = new Sale(0l,"First sale ", today, 1234, 100.0f, 2);
        Sale sale2 = new Sale(1l,"Second sale", yesterday, 5678, 200.0f, 4);
        List<Sale> list = new ArrayList<Sale>();
        list.add(sale1);
        list.add(sale2);
        
        when(repository.findAll()).thenReturn(list);
        
        // Execute the service call
        List<Sale> salesResult = service.getAllSales();

        // Assert the response
        Assertions.assertEquals(2, salesResult.size(), "getAllSales should return 2 sales");

	}

    @Test
    @DisplayName("Get all current sales")
	void testGetCurrentSales() {
		LocalDate today = LocalDate.now();
	    LocalDate yesterday = today.minusDays( 1 );
        // Setup our mock repository
        Sale sale1 = new Sale(0l,"First sale ", today, 1234, 100.0f, 2);
        Sale sale2 = new Sale(1l,"Second sale", today, 5678, 200.0f, 4);
        List<Sale> list = new ArrayList<Sale>();
        list.add(sale1);
        list.add(sale2);
        
        when(repository.getCurrentSales(LocalDate.now())).thenReturn(list);
        
        // Execute the service call
        List<Sale> salesResult = service.getCurrentSales();

        // Assert the response
        Assertions.assertEquals(2, salesResult.size(), "testGetCurrentSales should return 2 sales");

	}

}
