package com.prs.model.entity;

import java.time.LocalDate;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prs.util.DeliveryModeUtility;
import com.prs.util.StatusUtility;

import jakarta.persistence.*;

@Entity
public class Request {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "UserId")
	@JsonIgnoreProperties( value = "requests", allowSetters = true)
	private User user;
	private String description;
	private String justification;
	private LocalDate dateNeeded;
	private String deliveryMode;
	private String status;
	private double total;
	private LocalDate submittedDate;
	private String reasonForRejection;
	@OneToMany
	@JoinColumn(name = "RequestId")
	@JsonIgnoreProperties("request")
	private Collection<LineItem> lineItems;
	
	public Request() {
		super();
	}

	public Request(int id, User user, String description, String justification, LocalDate dateNeeded,
			String deliveryMode, String status, double total, LocalDate submittedDate, String reasonForRejection,
			Collection<LineItem> lineItems) {
		super();
		this.id = id;
		this.user = user;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.status = status;
		this.total = total;
		this.submittedDate = submittedDate;
		this.reasonForRejection = reasonForRejection;
		this.lineItems = lineItems;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public LocalDate getDateNeeded() {
		return dateNeeded;
	}

	public void setDateNeeded(LocalDate dateNeeded) {
		this.dateNeeded = dateNeeded;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public LocalDate getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(LocalDate submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getReasonForRejection() {
		return reasonForRejection;
	}

	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}
	
	public Collection<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(Collection<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public String validate() {
		String errors = "";
		// Validate user
		if(user == null) {errors += "User is required;";}
		// Validate description
		if(description == null || description.isEmpty()) { errors += "Description is required;"; }
		else if(description.length() > 100) { errors += "Description must be 100 characters or less;"; }
		// Validate justification
		if(justification == null || justification.isEmpty()) {errors += "Justification is required;";} 
		else if(justification.length() > 255) {errors += "Justification must be 255 characters or less;";}
		// Validate dateNeeded
		if(dateNeeded == null) {errors += "Date Needed is required;";}
		else if(dateNeeded.isBefore(LocalDate.now())) {errors += "Date Needed must be in the future required;";};
		// Validate deliveryMode
		if(deliveryMode == null) { deliveryMode = DeliveryModeUtility.PICKUP_DELIVERY; }
		else if(!deliveryMode.equalsIgnoreCase(DeliveryModeUtility.PICKUP_DELIVERY) && !deliveryMode.equalsIgnoreCase(DeliveryModeUtility.MAIL_DELIVERY)) {
			errors += "Delivery Mode must be " + DeliveryModeUtility.PICKUP_DELIVERY + " or " + DeliveryModeUtility.MAIL_DELIVERY + ";";
		}
		// Validate reasonForRejection
		if(reasonForRejection != null) {errors += "Reason For Reject can not be added until the status is " + StatusUtility.STATUS_REJECTED + ";";}
		// Validate Status
		if(!status.equalsIgnoreCase( StatusUtility.STATUS_NEW)) { errors += "Status must be " + StatusUtility.STATUS_NEW + " to edit;";}
		return errors;
	}
}
