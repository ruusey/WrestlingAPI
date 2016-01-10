package com.sms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.logging.Logger;
import com.models.LogData;

public class ContactReader {
	public List<String> getEmails() throws Exception{
		Logger logger = new Logger();
		LogData lData = new LogData();
		
		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
	List<String> res = new ArrayList<String>();

	try {
		   Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException ex) {
			lData.setLogData("unable to load driver class!");
			lData.setType("error");

		}
	String url= "jdbc:mysql://localhost:3306/wrestling";
	String USER = "root";
	String PASS = "";
	Connection conn = null;
	String SQL = "SELECT w.email FROM wrestlers w WHERE w.contact=2 AND w.deleted is null";
	try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection(url, USER, PASS);
		 Statement st = conn.createStatement();
		 
		 ResultSet rs = st.executeQuery(SQL);
		 while (rs.next()){
			 res.add(rs.getString(1));
		 }
		 
		 
		lData.setLogData("Sucessfully Retrieved All Wrestlers");
		lData.setType("info");

	} catch (Exception e) {
		lData.setLogData(e.getMessage());
		lData.setType("error");
		throw e;
		
	}finally{
		conn.close();
	}
	logger.Log(lData);
	return res;

}
	public List<String> getNumbers() throws Exception{
		Logger logger = new Logger();
		LogData lData = new LogData();
		
		lData.setWriteToConsole(true);
		lData.setSource(this.getClass().getName());
	List<String> res = new ArrayList<String>();

	try {
		   Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException ex) {
			lData.setLogData("unable to load driver class!");
			lData.setType("error");

		}
	String url= "jdbc:mysql://localhost:3306/wrestling";
	String USER = "root";
	String PASS = "";
	Connection conn = null;
	String SQL = "SELECT w.phone FROM wrestlers w WHERE w.contact=1 AND w.deleted is null";
	try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection(url, USER, PASS);
		 Statement st = conn.createStatement();
		 
		 ResultSet rs = st.executeQuery(SQL);
		 while (rs.next()){
			 res.add(rs.getString(1));
		 }
		 
		 
		lData.setLogData("Sucessfully Retrieved All Phone Numbers");
		lData.setType("info");

	} catch (Exception e) {
		lData.setLogData(e.getMessage());
		lData.setType("error");
		throw e;
		
	}finally{
		conn.close();
	}
	logger.Log(lData);
	return res;

}
}
