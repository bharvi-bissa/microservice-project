package com.app.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRequestModel {

	@NotNull(message = "First name cannot be null")
	@Size(min = 2, message = "First name cannot be less than two characters")
	private String firstName;

	@NotNull(message = "First name cannot be null")
	@Size(min = 2, message = "First name cannot be less than two characters")
	private String lastName;

	@Email
	@NotNull(message = "Email cannot be null")
	private String email;

	@NotNull(message = "Password cannot be null")
	@Size(min = 8, max = 16, message = "Password should have minimum 8 and maximum 16 characters")
	private String password;

	public UserRequestModel() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firsName) {
		this.firstName = firsName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserRequestModel [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + "]";
	}
	
	
}
