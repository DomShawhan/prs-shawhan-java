package com.prs.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.prs.db.RequestRepo;
import com.prs.db.UserRepo;
import com.prs.exception.ResponseException;
import com.prs.model.entity.Request;
import com.prs.model.entity.User;
import com.prs.util.StatusUtility;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestController {
	@Autowired
	private RequestRepo requestRepo;
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/")
	public List<Request> getAllRequests() {
		try {
			return requestRepo.findAll();
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@GetMapping("/{id}")
	public Request getRequestById(@PathVariable int id) {
		try {
			Optional<Request> r = requestRepo.findById(id);
			if(r.isPresent()) {
				return r.get();
			}
			throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "Request not found");
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@PostMapping("")
	public Request postRequest(@RequestBody Request request) {
		try {
			// Set default status and submittedDate
			request.setStatus(StatusUtility.STATUS_NEW);
			request.setSubmittedDate(LocalDate.now());
			request.setTotal(0);
			request.setReasonForRejection(null);
			// Validate the data
			String errors = request.validate();
			if(errors.isEmpty()) {
				return requestRepo.save(request);
			}
			throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", errors);
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@PutMapping("/{id}")
	public Request putRequest(@PathVariable int id, @RequestBody Request request) {
		try {
			Request r = null;
			Optional<Request> requestOptional = requestRepo.findById(id);
			// Check that the request id matches that path id
			if(id != request.getId()) {
				throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "Request id does not match path id");
			//Check that the request exists
			} else if(!requestOptional.isPresent()) {
				throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "Request not found");
			} else {
				//Validate the data
				Request oldRequest = requestOptional.get();
				oldRequest.setDescription(request.getDescription());
				oldRequest.setDateNeeded(request.getDateNeeded());
				oldRequest.setDeliveryMode(request.getDeliveryMode());
				oldRequest.setJustification(request.getJustification());
				
				String errors = oldRequest.validate();
				if(errors.isEmpty() ) {
					r = requestRepo.save(oldRequest);
				} else {
					throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", errors);
				}
			}
			return r;
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteRequest(@PathVariable int id) {
		try {
			boolean success = false;
			Optional<Request> request = requestRepo.findById(id);
			if(request.isPresent() && request.get().getStatus().equalsIgnoreCase(StatusUtility.STATUS_NEW)) {
				requestRepo.deleteById(id);
				success = true;
			} else {
				throw new ResponseException(HttpStatus.NOT_FOUND, " Delete Error", "Request not found");
			}
			return success;
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@PostMapping("/review/{id}")
	public Request reviewRequest(@PathVariable int id) {
		try {
			Optional<Request> request = requestRepo.findById(id);
			if(!request.isPresent()) {
				throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "Request not found");
			}
			Request r = request.get();
			if(!r.getStatus().equalsIgnoreCase(StatusUtility.STATUS_NEW)) {
				throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "Request does not have a status of new");
			}
			if(r.getTotal() <= 50) { r.setStatus(StatusUtility.STATUS_APPROVED); }
			else { r.setStatus(StatusUtility.STATUS_REVIEW);}
			
			return requestRepo.save(r);
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@PostMapping("/approve/{id}")
	public Request approveRequest(@PathVariable int id) {
		try {
			Optional<Request> request = requestRepo.findById(id);
			if(!request.isPresent()) {
				throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "Request not found");
			}
			Request r = request.get();
			if(!r.getStatus().equalsIgnoreCase(StatusUtility.STATUS_REVIEW)) {
				throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "Request does not have a status of review");
			}
			r.setStatus(StatusUtility.STATUS_APPROVED);
			
			return requestRepo.save(r);
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@PostMapping("/reject/{id}")
	public Request rejectRequest(@PathVariable int id, @RequestBody String reason) {
		try {
			Optional<Request> request = requestRepo.findById(id);
			if(!request.isPresent()) {
				throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "Request not found");
			}
			
			Request r = request.get();
			if(!r.getStatus().equalsIgnoreCase(StatusUtility.STATUS_REVIEW)) {
				throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "Request does not have a status of " + StatusUtility.STATUS_REVIEW);
			}
			
			if(reason == null || reason.isBlank()) {
				throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "Rejection reason not specified");
			} else if(reason.length() > 100) {
				throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "Rejection reason must be less than 100 characters");
			}
			r.setStatus(StatusUtility.STATUS_REJECTED);
			r.setReasonForRejection(reason);
			
			return requestRepo.save(r);
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@GetMapping("/reviews/{userid}")
	public List<Request> getReviewableRequests(@PathVariable int userid) {
		try {
			Optional<User> u = userRepo.findById(userid);
			if(u.isPresent()) {
				if(u.get().isReviewer()) {
					List<Request> requests = requestRepo.findAllByStatusAndUserIdIsNot(StatusUtility.STATUS_REVIEW, userid);
					return requests;
				} else {
					return new ArrayList<Request>();
				}
			}
			throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "User not found");
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
}
