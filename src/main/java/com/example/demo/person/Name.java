package com.example.demo.person;

public class Name {
	private String firstName;
	private String secondtName;
	
	public Name() {
	}
	
	
	public Name(String firstName, String secondtName) {
		super();
		this.firstName = firstName;
		this.secondtName = secondtName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSecondtName() {
		return secondtName;
	}
	public void setSecondtName(String secondtName) {
		this.secondtName = secondtName;
	}
	
	
}
