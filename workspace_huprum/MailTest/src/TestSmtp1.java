import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestSmtp1 {

	public static void main(String[] args) {

	
		final String username = "tuktuk01@internet.ru";
		final String password = "TTOG2y_rtpi4";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.mail.ru");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.class",
			          "javax.net.ssl.SSLSocketFactory");
		 props.put("mail.smtp.socketFactory.port", "465");
		Session session = Session.getInstance(props, new javax.mail.Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(username, password);
			}
		});
		try
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("yaa52@mail.ru"));
			message.setSubject("subject");
			message.setText("msg");
			Transport.send(message);
			//return true;
		} catch (MessagingException e)
		{
			throw new RuntimeException(e);
		}

	}

}
