package com.myBank.model;

public class users {
private String username, password, fName, lName,role,email;


public users() {
	
}

public users(String username, String password, String fName, String lName, String role, String email) {
	
	this.email = email;
	this.fName= fName;
	this.lName = lName;
	this.password = password;
	this.role = role;
	this.username= username;
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


public String getfName() {
	return fName;
}


public void setfName(String fName) {
	this.fName = fName;
}


public String getlName() {
	return lName;
}


public void setlName(String lName) {
	this.lName = lName;
}


public String getRole() {
	return role;
}


public void setRole(String role) {
	this.role = role;
}


public String getEmail() {
	return email;
}


public void setEmail(String email) {
	this.email = email;
}

public String toString() {
	return "[ Username: "+username+" Password: "+password+" First Name: "+fName+" Last Name: "+ lName+ " Role:" +role+ " E-mail: "+email+" ]";
}

}