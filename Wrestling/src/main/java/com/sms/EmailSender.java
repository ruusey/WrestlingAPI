package com.sms;
import java.util.List;
import java.util.Properties;

import javax.mail.*;  
import javax.mail.internet.*;
public class EmailSender {
	public static  void send(List<String> str , String msg) {
		if(str==null){
			return;
		}
        final String username = "notification.westminster@gmail.com";
        final String password = "ruboy123";

        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true"); // added this line
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", "username");
        props.put("mail.smtp.password", "password");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");



        Session session = Session.getInstance(props,null);
        MimeMessage message = new MimeMessage(session);

        System.out.println("Port: "+session.getProperty("mail.smtp.port"));

        // Create the email addresses involved
        try {
            InternetAddress from = new InternetAddress(username);
            message.setSubject("Westminster Wrestling Update");
            message.setFrom(from);
            //Who to send to
            for(String s: str){
            	message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(s));
            }
            
            
            // Create a multi-part to combine the parts
            Multipart multipart = new MimeMultipart("alternative");

            // Create your text message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("New Email Received From Westminster Wrestling /n /n Message Content: "+msg+"/n /n Thanks For Using Westminster Wrestling Manager");

            // Add the text part to the multipart
            multipart.addBodyPart(messageBodyPart);

            // Create the html part
            messageBodyPart = new MimeBodyPart();
            String htmlMessage = "New Email Received From Westminster Wrestling <br> <br> Message Content: "+msg+"<br> <br> Thanks For Using Westminster Wrestling Manager";
            messageBodyPart.setContent(htmlMessage, "text/html");


            // Add html part to multi part
            multipart.addBodyPart(messageBodyPart);

            // Associate multi-part with message
            message.setContent(multipart);

            // Send message
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", username, password);
            
            System.out.println("Transport: "+transport.toString());
            transport.sendMessage(message, message.getAllRecipients());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
