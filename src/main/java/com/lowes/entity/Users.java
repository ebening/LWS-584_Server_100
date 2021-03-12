package com.lowes.entity;

public class Users {
	private String name;
	private String dob;
	private String email;
	private String phone;
 
	public Users(String name, String dob, String email, String phone,
			String address, Long pincode, String country) {
		super();
		this.name = name;
		this.dob = dob;
		this.email = email;
		this.phone = phone;
	}
 
	public Users() {
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public String getDob() {
		return dob;
	}
 
	public void setDob(String dob) {
		this.dob = dob;
	}
 
	public String getEmail() {
		return email;
	}
 
	public void setEmail(String email) {
		this.email = email;
	}
 
	public String getPhone() {
		return phone;
	}
 
	public void setPhone(String phone) {
		this.phone = phone;
	}
}