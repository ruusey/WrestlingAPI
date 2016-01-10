package com.models;

public class WrestlerChange {
	public String currentName;
	public boolean nameChange;
	public String data;
	
	public WrestlerChange(){
		
	}
	public WrestlerChange(String currentName, boolean nameChange, String data){
		this.currentName=currentName;
		this.nameChange=nameChange;
		this.data=data;
	}
	public String getCurrentName() {
		return currentName;
	}
	public void setCurrentName(String currentName) {
		this.currentName = currentName;
	}
	public boolean isNameChange() {
		return nameChange;
	}
	public void setNameChange(boolean nameChange) {
		this.nameChange = nameChange;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
