package com.models;

public class MatchLogItem {
	public String matchName;
	public int period;
	public String wrestler;
	public String pointType;
	public int pointScore;
	public String date = null;
	public MatchLogItem(){
		
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getWrestler() {
		return wrestler;
	}
	public void setWrestler(String wrestler) {
		this.wrestler = wrestler;
	}
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public int getPointScore() {
		return pointScore;
	}
	public void setPointScore(int pointScore) {
		this.pointScore = pointScore;
	}
	public String getMatchName() {
		return matchName;
	}
	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
