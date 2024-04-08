package com.prs.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
public class LineItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "RequestId")
	@JsonIgnoreProperties("lineItems")
	private Request request;
	@ManyToOne
	@JoinColumn(name = "ProductId")
	@JsonIgnoreProperties({"lineItems", "vendor"})
	private Product product;
	private int quantity;
	
	public LineItem() {
		super();
	}

	public LineItem(int id, Request request, Product product, int quantity) {
		super();
		this.id = id;
		this.request = request;
		this.product = product;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String validate() {
		String errors = "";
		// Validate request
		if(request == null) {errors += "Request is required;";} 
		// Validate product
		if(product == null) {errors += "Product is required;";} 
		//Validate quantity
		if(quantity <= 0) {errors += "Quantity must be greater than 0";}
		
		return errors;
	}
}
