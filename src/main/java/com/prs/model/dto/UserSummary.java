package com.prs.model.dto;
// DTO to send User Summary data to client
public class UserSummary {
	private String firstname;
	private String lastname;
	private int countOfRejectedRequests;
	private int countOfAcceptedRequests;
	private int countOfPendingRequests;
	private double approvedTotal;
	private double rejectedTotal;
	
	public UserSummary() {
		super();
	}

	public UserSummary(String firstname, String lastname, int countOfRejectedRequests, int countOfAcceptedRequests,
			int countOfPendingRequests, double approvedTotal, double rejectedTotal) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.countOfRejectedRequests = countOfRejectedRequests;
		this.countOfAcceptedRequests = countOfAcceptedRequests;
		this.countOfPendingRequests = countOfPendingRequests;
		this.approvedTotal = approvedTotal;
		this.rejectedTotal = rejectedTotal;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getCountOfRejectedRequests() {
		return countOfRejectedRequests;
	}

	public void setCountOfRejectedRequests(int countOfRejectedRequests) {
		this.countOfRejectedRequests = countOfRejectedRequests;
	}

	public int getCountOfAcceptedRequests() {
		return countOfAcceptedRequests;
	}

	public void setCountOfAcceptedRequests(int countOfAcceptedRequests) {
		this.countOfAcceptedRequests = countOfAcceptedRequests;
	}

	public int getCountOfPendingRequests() {
		return countOfPendingRequests;
	}

	public void setCountOfPendingRequests(int countOfPendingRequests) {
		this.countOfPendingRequests = countOfPendingRequests;
	}

	public double getApprovedTotal() {
		return approvedTotal;
	}

	public void setApprovedTotal(double approvedTotal) {
		this.approvedTotal = approvedTotal;
	}

	public double getRejectedTotal() {
		return rejectedTotal;
	}

	public void setRejectedTotal(double rejectedTotal) {
		this.rejectedTotal = rejectedTotal;
	}	
}
