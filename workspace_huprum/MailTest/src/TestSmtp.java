import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestSmtp {

	public static void main(String[] args) {
	    Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.mail.ru");
	    props.put("mail.smtp.socketFactory.port", "465");
	   // props.put("mail.smtp.socketFactory.class",
	    //        "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.port", "465"); 
	    Session session = Session.getDefaultInstance(props,
	        new javax.mail.Authenticator() {
	                            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication("tuktuk01@internet.ru","TTOG2y_rtpi4");
	            }
	        });

	    try {

	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("tuktuk01@internet.ru"));
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse("yaa52@mail.ru"));
	        message.setSubject("Testing Subject");
	        message.setText("Test Mail");

	        Transport.send(message);

	        System.out.println("Done");

	    } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }
	}

}
