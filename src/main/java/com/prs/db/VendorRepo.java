package com.prs.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.entity.Vendor;

public interface VendorRepo extends JpaRepository<Vendor, Integer> {
	// SELECT * FROM Vendor WHERE Code = ?
	// returns a boolean
	boolean existsByCode(String code);
}
