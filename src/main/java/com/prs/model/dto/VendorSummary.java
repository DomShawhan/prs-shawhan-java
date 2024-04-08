package com.prs.model.dto;
// DTO to send Vendor Summary data to the client
public class VendorSummary {
	private String code;
	private String name;
	private int countOfProducts;
	
	public VendorSummary() {
		super();
	}

	public VendorSummary(String code, String name, int countOfProducts) {
		super();
		this.code = code;
		this.name = name;
		this.countOfProducts = countOfProducts;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCountOfProducts() {
		return countOfProducts;
	}

	public void setCountOfProducts(int countOfProducts) {
		this.countOfProducts = countOfProducts;
	}
	
	
}
