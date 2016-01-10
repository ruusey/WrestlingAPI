package com.models;

public class DeleteRequest {
	public String name;
	public DeleteRequest(){
		
	}
	public DeleteRequest(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
