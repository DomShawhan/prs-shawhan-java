package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.prs.db.ProductRepo;
import com.prs.exception.ResponseException;
import com.prs.model.entity.Product;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private ProductRepo productRepo;
	
	@GetMapping("/")
	public List<Product> getAllProducts(){
		try {
			return productRepo.findAll();
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable int id) {
		try {
			Optional<Product> p = productRepo.findById(id);
			if(p.isPresent()) {
				return p.get();
			}
			throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "Product Not Found");
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@PostMapping("")
	public Product postProduct(@RequestBody Product product) {
		try {
			// Check the PartNumber and VendorId for uniqueness
			if(product.getVendor() != null) {
				if(!productRepo.existsByPartNumberAndVendorId(product.getPartNumber(), product.getVendor().getId())) {
					//validate the data
					String errors = product.validate();
					if(errors.isEmpty()) {
						return productRepo.save(product);
					}
					throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", errors);
				}
				throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "The specified vendor already has the part number");
			}
			throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "Vendor cannot be null");
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@PutMapping("/{id}")
	public Product putProduct(@PathVariable int id, @RequestBody Product product) {
		try {
			Product p = null;
			// Check that the product id matches that path id
			if(id != product.getId()) {
				throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "Product id does not match the path id");
			//Check that the product exists
			} else if(!productRepo.existsById(id)) {
				throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "Product Not Found");
			} else {
				// Validate the data
				String errors = product.validate();
				if(errors.isEmpty()) {
					// Check the that the unique fields have not changed or are unique
					Product oldProduct = productRepo.findById(product.getId()).get();
					if((oldProduct.getVendor().getId() == product.getVendor().getId() 
							&& oldProduct.getPartNumber().equalsIgnoreCase(product.getPartNumber()))
							|| !productRepo.existsByPartNumberAndVendorId(product.getPartNumber(), product.getVendor().getId())) {
						p = productRepo.save(product);
					} else {
						throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "Combination of Vendor and Product must be unique");
					}
				} else {
					throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", errors);
				}
			}
			return p;
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteProduct(@PathVariable int id) {
		try {
			boolean success = false;
			if(productRepo.existsById(id)) {
				productRepo.deleteById(id);
				success = true;
			} else {
				throw new ResponseException(HttpStatus.NOT_FOUND, "Delete Error", "Product Not Found");
			}
			
			return success;
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
}
