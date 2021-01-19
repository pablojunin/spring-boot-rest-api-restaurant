package com.restaurant.demo.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.restaurant.demo.dao.ISaleRepository;
import com.restaurant.demo.model.Sale;

@Service
@Valid
public class SaleServiceImpl implements ISaleService {

	@Autowired
	private ISaleRepository saleRepository;
	
	public SaleServiceImpl(ISaleRepository saleRepository) {
		super();
		this.saleRepository = saleRepository;
	}

	@Override
	public Sale createSale(@Valid Sale sale) {
		Sale createdSale = this.saleRepository.save(sale);
		return createdSale;
	}

	@Override
	public List<Sale> getAllSales() {
		return this.saleRepository.findAll();
	}
	
	@Override
	public List<Sale> getCurrentSales() {
		return this.saleRepository.getCurrentSales(LocalDate.now());
	}

	public ISaleRepository getSaleRepository() {
		return saleRepository;
	}

	public void setSaleRepository(ISaleRepository saleRepository) {
		this.saleRepository = saleRepository;
	}
}
