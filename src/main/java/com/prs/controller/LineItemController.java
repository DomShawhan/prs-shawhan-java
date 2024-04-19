package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.prs.db.LineItemRepo;
import com.prs.db.ProductRepo;
import com.prs.db.RequestRepo;
import com.prs.exception.ResponseException;
import com.prs.model.entity.LineItem;
import com.prs.model.entity.Product;
import com.prs.model.entity.Request;
import com.prs.util.StatusUtility;

@CrossOrigin
@RestController
public class LineItemController {
	@Autowired
	private LineItemRepo lineItemRepo;
	@Autowired
	private RequestRepo requestRepo;
	@Autowired
	private ProductRepo productRepo;
	
	@GetMapping("/api/lineitems/")
	public List<LineItem> getAllLineItems() {
		try {
			return lineItemRepo.findAll();
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@GetMapping("/api/lineitems/{id}")
	public LineItem getLineItemById(@PathVariable int id) {
		try {
			Optional<LineItem> li = lineItemRepo.findById(id);
			if(li.isPresent()) {
				return li.get();
			}
			throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "Line Item Not Found");
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@PostMapping("/api/lineitems")
	public LineItem postLineItem(@RequestBody LineItem lineItem) {
		try {
			LineItem li = null;
			//validate the data
			String errors = lineItem.validate();
			if(errors.isEmpty()) {
				// Check that the request and product exist
				Optional<Request> request = requestRepo.findById(lineItem.getRequest().getId());
				Optional<Product> product = productRepo.findById(lineItem.getProduct().getId());
				if(request.isPresent()) {
					Request r = request.get();
					// Check that the status of the request is new
					if(r.getStatus().equalsIgnoreCase(StatusUtility.STATUS_NEW)) {
						if(product.isPresent()) {
							// If the line Item already exists, add the qty to it
							if(lineItemRepo.existsByProductIdAndRequestId(lineItem.getProduct().getId(), lineItem.getRequest().getId())) {
								LineItem existingLI = lineItemRepo.findById(lineItem.getId()).get();
								
								existingLI.setQuantity(lineItem.getQuantity() + existingLI.getQuantity());
								
								li = lineItemRepo.save(existingLI);
							} else {
								//else create a new line item 
								li = lineItemRepo.save(lineItem);
								recalculateRequestTotal(li.getRequest().getId());
							}
						} else {
							throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "The product does not exist");
						}
					} else {
						throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "The request does not have a status of new");
					}
					
				} else {
					throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "The request does not exist");
				}
			} else {
				throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", errors);
			}
			
			return li;
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@PutMapping("/api/lineitems/{id}")
	public LineItem putLineItem(@PathVariable int id, @RequestBody LineItem lineItem) {
		try {
			LineItem li = null;
			//validate the data
			String errors = lineItem.validate();
			if(errors.isEmpty()) {
				// check that the request and product exists
				Optional<Request> request = requestRepo.findById(lineItem.getRequest().getId());
				Optional<Product> product = productRepo.findById(lineItem.getProduct().getId());
				if(request.isPresent()) {
					Request r = request.get();
					// Check that the request status is new
					if(r.getStatus().equalsIgnoreCase(StatusUtility.STATUS_NEW)) {
						if(product.isPresent()) {
							// Check that the lineItem id matches that path id
							if(id != lineItem.getId()) {
								throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "Line Item does not match the path id");
							//Check that the line item exists
							} else if (!lineItemRepo.existsById(id)) {
								throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "Line Item Not Found");
							} else {
								// Get the old item
								LineItem oldLI = lineItemRepo.findById(lineItem.getId()).get();
								// If the unique fields are the same, update the data
								if (oldLI.getRequest().getId() == lineItem.getRequest().getId() && oldLI.getProduct().getId() == lineItem.getProduct().getId()) {
									li = lineItemRepo.save(lineItem);
									recalculateRequestTotal(li.getRequest().getId());
								//Else make sure the new unique fields are unique
								} else if (lineItemRepo.existsByProductIdAndRequestId(lineItem.getProduct().getId(), lineItem.getRequest().getId())) {
									LineItem existingLI = lineItemRepo.findById(lineItem.getId()).get();
									existingLI.setQuantity(existingLI.getQuantity() + lineItem.getQuantity());
									li = lineItemRepo.save(existingLI);
									recalculateRequestTotal(li.getRequest().getId());
								} else if (!lineItemRepo.existsByProductIdAndRequestId(lineItem.getProduct().getId(), lineItem.getRequest().getId())) {
									li = lineItemRepo.save(lineItem);
									recalculateRequestTotal(li.getRequest().getId());
								}
							}
						} else {
							throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "The product does not exist");
						}
					} else {
						throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "The request does not have a status of new");
					}
					
				} else {
					throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", "The request does not exist");
				}
			} else {
				throw new ResponseException(HttpStatus.BAD_REQUEST, "Error", errors);
			}
			return li;
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@DeleteMapping("/api/lineitems/{id}")
	public boolean deleteLineItem(@PathVariable int id) {
		try {
			boolean success = false;
			// Check that the lineItem exists
			if(lineItemRepo.existsById(id)) {
				LineItem li = lineItemRepo.findById(id).get();
				int reqId = li.getRequest().getId();
				lineItemRepo.deleteById(id);
				recalculateRequestTotal(reqId);
				success = true;
			} else {
				throw new ResponseException(HttpStatus.NOT_FOUND, " Delete Error", "Line Item not found");
			}
			
			return success;
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@GetMapping("/api/lines-for-pr/{reqid}")
	public List<LineItem> getLineItemsForRequest(@PathVariable int reqid) {
		try {
			List<LineItem> lis = null;
			// Check that the request exists
			if(requestRepo.existsById(reqid)) {
				// Get all of the lineItems for the request
				lis = lineItemRepo.findAllByRequestId(reqid);
				return lis;
			}
			throw new ResponseException(HttpStatus.NOT_FOUND, "Error", "Request Not Found");
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	private void recalculateRequestTotal(int id) {
		List<LineItem> lis = lineItemRepo.findAllByRequestId(id);
		Request request = requestRepo.findById(id).get();
		double requestTotal = 0;
		for(LineItem li: lis) {
			Product p = productRepo.findById(li.getProduct().getId()).get();
			requestTotal += li.getQuantity() * p.getPrice();
		}
		request.setTotal(requestTotal);
		requestRepo.save(request);
	}
}
