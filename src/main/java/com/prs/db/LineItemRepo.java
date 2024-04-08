package com.prs.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.entity.LineItem;

public interface LineItemRepo extends JpaRepository<LineItem, Integer> {
	// SELECT * FROM LineItem WHERE RequestID = ?
	List<LineItem> findAllByRequestId(int id);
	// SELECT * FROM LineItem WHERE ProductID = ? AND RequestID = ?
	// returns a boolean
	boolean existsByProductIdAndRequestId(int productId, int requestId);
}
