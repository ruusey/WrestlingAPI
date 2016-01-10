package com.logging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.models.LogData;

public class Logger {

	
	public void Log(LogData l){
		try{
			if(l.writeToConsole){
				logConsole(l);
				logDB(l);
			}else{
				logDB(l);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	
	public void logDB(LogData l) throws Exception{
		try {
			   Class.forName("com.mysql.jdbc.Driver");
			}
			catch(ClassNotFoundException ex) {
			   System.out.println("Error: unable to load driver class!");
			}
		String url= "jdbc:mysql://localhost:3306/wrestling";
		String USER = "root";
		String PASS = "";
		try {
			Date dt = new Date();
			SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(url, USER, PASS);
			
			 String sql = "INSERT INTO logs (source, data, success, time)" +
			            "values (?, ?, ?, ?)";
			 //st.executeUpdate(sql);
			 
			 PreparedStatement preparedStmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			 preparedStmt.setString (1, l.source);
			 preparedStmt.setString (2, l.logData);
			 preparedStmt.setString (3, l.type);
			 preparedStmt.setString(4, sdf.format(dt));
			 
			 preparedStmt.execute();
			 

			 
		} catch (Exception e) {
			throw e;
			
		}

	}	
	
	public void logConsole(LogData l){
		System.out.println(("["+l.type+"] FROM "+l.source+" DATA: "+l.logData).toUpperCase());
	}
}
