package com.restaurant.demo.service;

import java.util.List;

import javax.validation.Valid;

import com.restaurant.demo.model.Sale;

public interface ISaleService {

	Sale createSale(Sale sale);

	List<Sale> getAllSales();

	List<Sale> getCurrentSales();

}