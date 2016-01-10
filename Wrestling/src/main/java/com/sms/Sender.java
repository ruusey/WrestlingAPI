package com.sms;

public class Sender {

	
	public boolean send(String s) throws Exception{
		ContactReader r = new ContactReader();
		
		try {
			TwillioSender.sendSMS(r.getNumbers(), s);
			EmailSender.send(r.getEmails(), s);
			return true;
		} catch (Exception e) {
			throw e;
			
		}
		

		
		
	}
}
