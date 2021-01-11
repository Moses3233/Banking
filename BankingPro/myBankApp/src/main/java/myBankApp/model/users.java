package myBankApp.model;

import java.sql.Date;

public class users {
private String fname;
private String lname;
private String username;
private String password;
private String role;
private Date dob;
private String gender;
private String email;
private String address;
private String zip;
private String city;
private String state;
private String country;

public String getFname() {
	return fname;
}
public void setFname(String fname) {
	this.fname = fname;
}
public String getLname() {
	return lname;
}
public void setLname(String lname) {
	this.lname = lname;
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
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public Date getDob() {
	return dob;
}
public void setDob(Date dob) {
	this.dob = dob;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getZip() {
	return zip;
}
public void setZip(String zip) {
	this.zip = zip;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}

@Override
public String toString() {
	
return "User [ Role: " + role + ", Username: " + username +", Password: " + password + 
			", First Name: " + fname + ", Last Name: " +lname+ ", Email: " + email +
			", DOB: " + dob + ", Gender: " +gender+ ", Address: " + address +
			", City: " + city + ", State: " +state+ ", ZIP: " + zip +", Country: " + country +" ]";

}

}
