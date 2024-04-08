package com.prs.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.entity.Request;

public interface RequestRepo extends JpaRepository<Request, Integer> {
	// SELECT * FROM Request WHERE Status = ? AND NOT UserId = ?
	List<Request> findAllByStatusAndUserIdIsNot(String status, int id);
	// SELECT * FROM Request WHERE UserId = ?
	List<Request> findAllByUserId(int id);
}
