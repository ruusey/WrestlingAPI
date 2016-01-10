
package com.sms;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
 

import java.util.ArrayList;
import java.util.List;
 
public class TwillioSender {
 
  // Find your Account Sid and Token at twilio.com/user/account
  public static final String ACCOUNT_SID = "AC8d0b7cf1b067b4942316aa9eca10b7a9";
  public static final String AUTH_TOKEN = "a173fce752d73452fdf77fe44de23ae0";
 
  public static void main(String[] args) throws TwilioRestException {
    TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
 
    // Build a filter for the MessageList
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("Body", "This is a test"));
    params.add(new BasicNameValuePair("To", "+16784272882"));
    params.add(new BasicNameValuePair("From", "+16789924987"));
 
    MessageFactory messageFactory = client.getAccount().getMessageFactory();
    Message message = messageFactory.create(params);
    System.out.println(message.getSid());
  }
  public static void sendSMS(List<String> numbers, String msg) throws TwilioRestException{
	  if(numbers==null){
		  return;
	  }
	    TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	 
	    // Build a filter for the MessageList
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("Body", msg));
	    for(String s:numbers){
	    	System.out.println(s);
	    	params.add(new BasicNameValuePair("To", s));
	    }
	    
	    params.add(new BasicNameValuePair("From", "+16789924987"));
	 
	    MessageFactory messageFactory = client.getAccount().getMessageFactory();
	    Message message = messageFactory.create(params);
	    System.out.println(message.getSid());
  }
}