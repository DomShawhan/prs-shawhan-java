package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.prs.db.ProductRepo;
import com.prs.db.VendorRepo;
import com.prs.exception.ResponseException;
import com.prs.model.dto.VendorSummary;
import com.prs.model.entity.Product;
import com.prs.model.entity.Vendor;

@CrossOrigin
@RestController
@RequestMapping("/api/vendors")
public class VendorController {
	@Autowired
	private VendorRepo vendorRepo;
	@Autowired 
	private ProductRepo productRepo;
	
	@GetMapping("/")
	public List<Vendor> getAllVendors() {
		try {
			return vendorRepo.findAll();
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@GetMapping("/{id}")
	public Vendor getVendorById(@PathVariable int id) {
		try {
			Optional<Vendor> v = vendorRepo.findById(id);
			if(v.isPresent()) {
				return v.get();
			}
			throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "Vendor not found");
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@PostMapping("")
	public Vendor postVendor(@RequestBody Vendor vendor) {
		try {
			// Validate the data
			String errors = vendor.validate();
			if(errors.isEmpty()) {
				// Check that the vendor code is unique
				if(!vendorRepo.existsByCode(vendor.getCode())) {
					return vendorRepo.save(vendor);
				}
				throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "The vendor name is already used");
			}
			throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", errors);
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@PutMapping("/{id}")
	public Vendor putVendor(@PathVariable int id, @RequestBody Vendor vendor) {
		try {
			Vendor v = null;
			// Check that the vendor id matches that path id
			if(id != vendor.getId()) {
				throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "Vendor id does not match path id");
			// Check that the vendor exists
			} else if(!vendorRepo.existsById(id)) {
				throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "Vendor not found");
			} else {
				// Validate the data
				String errors = vendor.validate();
				if(errors.isEmpty()) {
					// Check that the vendor code has not changed or that it is unique
					Vendor oldVendor = vendorRepo.findById(vendor.getId()).get();
					if(oldVendor.getCode().equalsIgnoreCase(vendor.getCode()) || !vendorRepo.existsByCode(vendor.getCode())) {
						oldVendor.setCode(vendor.getCode());
						oldVendor.setName(vendor.getName());
						oldVendor.setAddress(vendor.getAddress());
						oldVendor.setCity(vendor.getCity());
						oldVendor.setState(vendor.getState());
						oldVendor.setZip(vendor.getZip());
						oldVendor.setPhone(vendor.getPhone());
						oldVendor.setEmail(vendor.getEmail());
						v = vendorRepo.save(oldVendor);
					} else {
						throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "Vendor name is already taken");
					}
				} else {
					throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", errors);
				}
			}
			
			return v;
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteVendor(@PathVariable int id) {
		try {
			boolean success = false;
			
			if(vendorRepo.existsById(id)) {
				vendorRepo.deleteById(id);
				success = true;
			} else {
				throw new ResponseException(HttpStatus.NOT_FOUND, "Delete Error", "Vendor not found");
			}
			
			return success;
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@GetMapping("/vendorsummary/{id}")
	public VendorSummary getVendorSummary(@PathVariable int id) {
		try {
			Optional<Vendor> v = vendorRepo.findById(id);
			if(v.isPresent()) {
				Vendor vendor = v.get();
				List<Product> products = productRepo.findAllByVendorId(id);
				int countOfProducts = 0;
				
				// count the number of products that the vendor offers
				for(@SuppressWarnings("unused") Product p: products) {
					countOfProducts++;
				}
				
				return new VendorSummary(vendor.getCode(), vendor.getName(), countOfProducts);
			}
			throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "Vendor not found");
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
}
