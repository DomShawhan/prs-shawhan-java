package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.RequestRepo;
import com.prs.db.UserRepo;
import com.prs.exception.ResponseException;
import com.prs.model.dto.UserLogin;
import com.prs.model.dto.UserSummary;
import com.prs.model.entity.Request;
import com.prs.model.entity.User;
import com.prs.util.StatusUtility;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserRepo userRepo;
	@Autowired 
	private RequestRepo requestRepo;
	
	@GetMapping("/")
	public List<User> getAllUsers() {
		try {
			return userRepo.findAll();
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable int id) {
		try {
			Optional<User> u = userRepo.findById(id);
			if(u.isPresent()) {
				return u.get();
			}
			throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "User not found");
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@PostMapping("")
	public User postUser(@RequestBody User user) {
		try {
			// Check the username for uniqueness
			if(!userRepo.existsByUsername(user.getUsername())) {
				// Validate the data
				String errors = user.validate();
				if (errors.isEmpty()) {
					return userRepo.save(user);
				}
				throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", errors);
			}
			throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "User already exists");
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@PutMapping("/{id}")
	public User putUser(@PathVariable int id, @RequestBody User user) {
		try {
			User u = null;
			// Check that the user id matches that path id
			if(id != user.getId()) {
				throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "User id does not match path id");
			//Check that the user exists
			} else if(!userRepo.existsById(id)) {
				throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "User Not Found");
			} else {
				// Validate the data
				String errors = user.validate();
				if(errors.isEmpty()) {
					// Make sure that the username has not changed or that it is unique
					User oldUser = userRepo.findById(user.getId()).get();
					if(oldUser.getUsername().equalsIgnoreCase(user.getUsername()) || !userRepo.existsByUsername(user.getUsername())) {
						oldUser.setUsername(user.getUsername());
						oldUser.setFirstname(user.getFirstname());
						oldUser.setLastname(user.getLastname());
						oldUser.setPassword(user.getPassword());
						oldUser.setPhone(user.getPhone());
						oldUser.setEmail(user.getEmail());
						oldUser.setReviewer(user.isReviewer());
						oldUser.setAdmin(user.isAdmin());
						u = userRepo.save(oldUser);
					} else {
						throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "Username is already taken");
					}
				} else {
					throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", errors);
				}
			}
			return u;
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteUser(@PathVariable int id) {
		try {
			boolean success = false;
			if(userRepo.existsById(id)) {
				userRepo.deleteById(id);
				success = true;
			} else {
				throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "User Not Found");
			}
			return success;
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@PostMapping("/login")
	public User login(@RequestBody UserLogin ul) {
		try {
			User user = userRepo.findByUsernameAndPassword(ul.getUsername(), ul.getPassword());
			if(user == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or password not found");
			}
			return user;
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@GetMapping("/usersummary/{id}")
	public UserSummary getUserSummary(@PathVariable int id) {
		try {
			Optional<User> u = userRepo.findById(id);
			if(u.isPresent()) {
				User user = u.get();
				int countOfRejected = 0;
				int countOfAccepted = 0;
				int countOfPending = 0;
				double approvedTotal = 0;
				double rejectedTotal = 0;
				
				List<Request> requests = requestRepo.findAllByUserId(id);
				
				for(Request r: requests) {
					// If status is rejectd, add 1 to the rejected count and add the total to the rejected total
					if(r.getStatus().equalsIgnoreCase(StatusUtility.STATUS_REJECTED)) {
						countOfRejected++;
						rejectedTotal += r.getTotal();
					// If status is approved, add 1 to the approved count and add the total to the approved total
					} else if (r.getStatus().equalsIgnoreCase(StatusUtility.STATUS_APPROVED)) {
						countOfAccepted++;
						approvedTotal += r.getTotal();
					// If status is review, add 1 to the pending count
					} else if (r.getStatus().equalsIgnoreCase(StatusUtility.STATUS_REVIEW)) {
						countOfPending++;
					}
				}
				
				return new UserSummary(user.getFirstname(), user.getLastname(), countOfRejected, countOfAccepted, countOfPending, approvedTotal, rejectedTotal);
			}
			throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "User not found");
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
}
