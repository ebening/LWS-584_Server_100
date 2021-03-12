package com.lowes.testing;

import com.lowes.entity.Locacion;

public class Contact {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private Locacion locacion;
 
    public Contact() {
    }
 
    public Contact(String firstname, String lastname, String email, String phone, Locacion locacion) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.locacion = locacion;
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

	public Locacion getLocacion() {
		return locacion;
	}

	public void setLocacion(Locacion locacion) {
		this.locacion = locacion;
	}
	
	
    
    
     
}