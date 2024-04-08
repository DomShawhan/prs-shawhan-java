package com.prs.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	// SELECT * FROM User WHERE Username = ? AND Password = ?
	User findByUsernameAndPassword(String username, String password);
	// SELECT * FROM User WHERE Username = ? 
	// returns a boolean
	boolean existsByUsername(String username);
}
