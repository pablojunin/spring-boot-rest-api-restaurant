package com.restaurant.demo.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class SaleDTO {

	@NotEmpty
	private Integer id;
	
	private String description;
		
	private Integer billNumber;

	@Min(value = 0, message = "Amount should be upper to 0.")
	private Float amount;
	
	@Min(value = 0, message = "ItemsQuantity should be upper to 0.")
	private Integer itemsQuantity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Integer getItemsQuantity() {
		return itemsQuantity;
	}

	public void setItemsQuantity(Integer itemsQuantity) {
		this.itemsQuantity = itemsQuantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(Integer billNumber) {
		this.billNumber = billNumber;
	}
}
