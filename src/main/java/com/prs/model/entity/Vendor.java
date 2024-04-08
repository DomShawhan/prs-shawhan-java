package com.prs.model.entity;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
public class Vendor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String code;
	private String name;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String email;
	@OneToMany
	@JoinColumn(name = "VendorId")
	@JsonIgnoreProperties("vendor")
	private Collection<Product> products;
	
	public Vendor() {
		super();
	}

	public Vendor(int id, String code, String name, String address, String city, String state, String zip, String phone,
			String email, Collection<Product> products) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
		this.products = products;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
	
	public String validate() {
		String errors = "";
		// Validate code
		if(code == null || code.isEmpty()) {errors += "Code is required;";} 
		else if(code.length() > 10) {errors += "Code must be 10 characters or less;";}
		// Validate name
		if(name == null || name.isEmpty()) { errors += "Name is required;"; }
		else if(name.length() > 255) { errors += "Name must be 255 characters or less;"; }
		// Validate address
		if(address == null || address.isEmpty()) {errors += "Address is required;";} 
		else if(address.length() > 255) {errors += "Address must be 255 characters or less;";}
		// Validate city
		if(city == null || city.isEmpty()) {errors += "City is required;";} 
		else if(city.length() > 255) {errors += "City must be 255 characters or less;";}
		// Validate state
		if(state == null || state.isEmpty()) {errors += "State is required;";} 
		else if(state.length() > 2) {errors += "State must be 2 characters or less;";}
		// Validate zip
		if(zip == null || zip.isEmpty()) {errors += "Zip is required;";} 
		else if(zip.length() > 5) {errors += "Zip must be 5 characters or less;";}
		// Validate phone
		if(phone != null && phone.length() > 12) {errors += "Phone must be 12 characters or less;";}
		// Validate email
		if(email != null && email.length() > 75) {errors += "Email must be 75 characters or less;";}
		else if(email != null &&( email.indexOf("@") == -1 || email.indexOf(".") == -1 || email.indexOf(".") < email.indexOf("@"))) { errors += "Email is not a valid email"; }
		
		return errors;
	}
}
