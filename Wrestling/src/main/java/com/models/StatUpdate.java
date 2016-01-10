package com.models;

public class StatUpdate {
	public int wrestlerID;
	public String statID;
	public String type;
	public int score;
	public StatUpdate(){
		
	}
	public StatUpdate(int wID, String statID, String type, int score){
		this.wrestlerID=wID;
		this.statID=statID;
		this.type=type;
		this.score=score;
	}
	public int getWrestlerID() {
		return wrestlerID;
	}
	public void setWrestlerID(int wrestlerID) {
		this.wrestlerID = wrestlerID;
	}
	public String getStatID() {
		return statID;
	}
	public void setStatID(String statID) {
		this.statID = statID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
}
