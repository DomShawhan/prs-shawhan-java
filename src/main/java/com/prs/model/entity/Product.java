package com.prs.model.entity;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "VendorID")
	@JsonIgnoreProperties(value = "products", allowSetters = true)
	private Vendor vendor;
	private String partNumber;
	private String name;
	private double price;
	private String unit;
	private String photoPath;
	
	public Product() {
		super();
	}

	public Product(int id, Vendor vendor, String partNumber, String name, double price, String unit, String photoPath,
			Collection<LineItem> lineItems) {
		super();
		this.id = id;
		this.vendor = vendor;
		this.partNumber = partNumber;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.photoPath = photoPath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String validate() {
		String errors = "";
		// Validate vendor
		if(vendor == null) {errors += "Vendor is required;";} 
		// Validate partNumber
		if(partNumber == null || partNumber.isEmpty()) { errors += "Part Number is required;"; }
		else if(partNumber.length() > 50) { errors += "Part Number must be 50 characters or less;"; }
		// Validate price
		if(price < 0) { errors += "Price cannot be negative;"; }
		// Validate name
		if(name == null || name.isEmpty()) {errors += "Name is required;";} 
		else if(name.length() > 150) {errors += "Name must be 150 characters or less;";}
		// Validate unit
		if(unit == null || unit.isEmpty()) {errors += "Unit is required;";} 
		else if(unit.length() > 255) {errors += "Unit must be 255 characters or less;";}
		// Validate photPath
		if(photoPath != null && photoPath.length() > 255) {errors += "Photo Path must be 255 characters or less;";}
		
		return errors;
	}
	
}
