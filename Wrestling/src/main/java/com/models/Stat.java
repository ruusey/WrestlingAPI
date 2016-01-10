package com.models;

public class Stat {
	public int wrestlerID;
	public int takedown;
	public int escape;
	public int nearfall;
	public int pin;
	public int win;
	public int loss;
	public int teampoint;
	public int reversal;
	public int forfeit;
	public String wrestlerName;
	public Stat(){
		
	}
	
	

	public String getWrestlerName() {
		return wrestlerName;
	}



	public void setWrestlerName(String wrestlerName) {
		this.wrestlerName = wrestlerName;
	}



	public int getWrestlerID() {
		return wrestlerID;
	}
	public void setWrestlerID(int wrestlerID) {
		this.wrestlerID = wrestlerID;
	}
	public int getTakedown() {
		return takedown;
	}
	public void setTakedown(int takedowns) {
		this.takedown = takedowns;
	}
	public int getEscape() {
		return escape;
	}
	public void setEscape(int escapes) {
		this.escape = escapes;
	}
	public int getNearfall() {
		return nearfall;
	}
	public void setNearfall(int nearfall) {
		this.nearfall = nearfall;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pins) {
		this.pin = pins;
	}
	public int getWin() {
		return win;
	}
	public void setWin(int wins) {
		this.win = wins;
	}
	public int getLoss() {
		return loss;
	}
	public void setLoss(int losses) {
		this.loss = losses;
	}
	public int getTeampoint() {
		return teampoint;
	}
	public void setTeampoint(int teampoint) {
		this.teampoint = teampoint;
	}
	public int getReversal() {
		return reversal;
	}
	public void setReversal(int reversal) {
		this.reversal = reversal;
	}
	public int getForfeit() {
		return forfeit;
	}
	public void setForfeit(int forfeit) {
		this.forfeit = forfeit;
	}
	
	
	
}
