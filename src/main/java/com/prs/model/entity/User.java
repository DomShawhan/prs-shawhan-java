package com.prs.model.entity;

import java.util.Collection;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String phone;
	private String email;
	private boolean reviewer;
	private boolean admin;
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "UserId")
	@JsonIgnoreProperties("user")
	private Collection<Request> requests;
	
	public User(int id, String username, String password, String firstname, String lastname, String phone, String email,
			boolean reviewer, boolean admin, Collection<Request> requests) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.email = email;
		this.reviewer = reviewer;
		this.admin = admin;
		this.requests = requests;
	}
	
	public User() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isReviewer() {
		return reviewer;
	}

	public void setReviewer(boolean reviewer) {
		this.reviewer = reviewer;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Collection<Request> getRequests() {
		return requests;
	}

	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", phone=" + phone + ", email=" + email + ", reviewer=" + reviewer
				+ ", admin=" + admin + "]";
	}
	
	public String validate() {
		String errors = "";
		// Validate username
		if(username == null || username.isEmpty()) {errors += "Username is required;";} 
		else if(username.length() > 20) {errors += "Username must be 20 characters or less;";}
		// Validate password
		if(password == null || password.isEmpty()) { errors += "Password is required;"; }
		else if(password.length() > 10) { errors += "Password must be 10 characters or less;"; }
		// Validate firstname
		if(firstname == null || firstname.isEmpty()) {errors += "First name is required;";} 
		else if(firstname.length() > 20) {errors += "First name must be 20 characters or less;";}
		// Validate lastname
		if(lastname == null || lastname.isEmpty()) {errors += "Last name is required;";} 
		else if(lastname.length() > 20) {errors += "Last name must be 20 characters or less;";}
		// Validate phone
		if(phone != null && phone.length() > 12) {errors += "Phone must be 12 characters or less;";}
		// Validate email
		if(email != null && email.length() > 75) {errors += "Email must be 75 characters or less;";}
		else if(email != null &&( email.indexOf("@") == -1 || email.indexOf(".") == -1 || email.indexOf(".") < email.indexOf("@"))) { errors += "Email is not a valid email"; }
		
		return errors;
	}
	
}
