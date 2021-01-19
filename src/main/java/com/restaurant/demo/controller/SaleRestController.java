package com.restaurant.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.demo.dto.SaleDTO;
import com.restaurant.demo.model.Sale;
import com.restaurant.demo.service.ISaleService;

import io.swagger.annotations.ApiImplicitParam;

@RestController
@RequestMapping(path = "/api/v1/sales", produces = { "application/json" })
@Validated
public class SaleRestController {

	private static final String QUEUE_REQUEST = "QUEUE_REQUEST";
	private static final String QUEUE_RESULT = "QUEUE_RESULT";
	@Autowired
	private ISaleService saleService;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true,
    allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public List<Sale> getAllSales() {
		return this.saleService.getAllSales();
	}

	@GetMapping("/current")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true,
    allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public List<SaleDTO> getCurrentSales() {
		//send request
		jmsTemplate.convertAndSend(QUEUE_REQUEST, "getCurrentSales");
		
		//receive result
		List sales = (List) jmsTemplate.receiveAndConvert(QUEUE_RESULT);

		List<SaleDTO> salesDTO = new ArrayList<SaleDTO>();
		for (Object sale : sales) {
			SaleDTO saleTemp = modelMapper.map(sale, SaleDTO.class);
			salesDTO.add(saleTemp);
		}
		
		return salesDTO;
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true,
    allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public SaleDTO createSale(@Validated @RequestBody Sale sale) {
		Sale createSale2 = this.saleService.createSale(sale);
		SaleDTO createSale = this.modelMapper.map(createSale2, SaleDTO.class);
		return createSale;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
		return new ResponseEntity<>("Error de validación: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
