package com.prs.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.entity.Product;


public interface ProductRepo extends JpaRepository<Product, Integer> {
	// SELECT * FROM Product WHERE VendorID = ?
	List<Product> findAllByVendorId(int id);
	// SELECT * FROM Product WHERE PartNumber = ? AND VendorID = ?
	// returns a boolean
	boolean existsByPartNumberAndVendorId(String partNumber, int vendorId);
}
