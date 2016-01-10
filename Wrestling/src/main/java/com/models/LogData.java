package com.models;

public class LogData {
	public String logData;
	public String source;
	public boolean writeToConsole;
	public String type;
	public LogData(String logData, String source, boolean writeToConsole, String type){
		this.logData=logData;
		this.source=source;
		this.writeToConsole=writeToConsole;
		this.type=type;
	}
	public LogData(){
		
	}
	public String getLogData() {
		return logData;
	}
	public void setLogData(String logData) {
		this.logData = logData;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public boolean isWriteToConsole() {
		return writeToConsole;
	}
	public void setWriteToConsole(boolean writeToConsole) {
		this.writeToConsole = writeToConsole;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
