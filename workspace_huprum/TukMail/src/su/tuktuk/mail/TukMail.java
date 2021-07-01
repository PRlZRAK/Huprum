package su.tuktuk.mail;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class TukMail
{
	private Runnable     scan;
	Process              p;
	private OutputStream out;
	private String       mail_to;
	private String       object_to;
	private String       text_mail;

	public TukMail(String[] args) throws IOException, InterruptedException
	{
		mail_to = args[0];
		object_to = args[1];
		text_mail = args[2];
		// p = Runtime.getRuntime().exec("java -jar bot.jar");
		p = Runtime.getRuntime().exec(
				"openssl s_client -connect smtp.email.eu-frankfurt-1.oci.oraclecloud.com:25 -starttls smtp -crlf");
		out = p.getOutputStream();
		scan = new Scan(this);
		new Thread(scan).start();
		System.out.println(printOut());
		put("EHLO there");
		System.out.println(printOut());
		put("AG9jaWQxLnVzZXIub2MxLi5hYWFhYWFhYWRnNm11eXhkbHpna3R6em51c21hemptdWZvN3h5ZXdrNjY0MmxoaDJwbjV4YmJ3c3BjbWFAb2NpZDEudGVuYW5jeS5vYzEuLmFhYWFhYWFhcHlvbnBoNmN3NDVsamNkcG5sNHFydXozYjJoa242NHFranJkM2IzYmpzYTRpdWxlaDZxYS4yNy5jb20AQmRBZzwtYUtWMyhTMUJTM29CTEU=");
		System.out.println(printOut());
		put("MAIL FROM: <robot@tuktuk.su>");
		System.out.println(printOut());
		put("rcpt to: " + mail_to);
		System.out.println(printOut());
		put("DATA");
		System.out.println(printOut());
		put("From: \"Robot Tuktuk\" <robot@tuktuk.su>");
		System.out.println(printOut());
		put("To: \"" + object_to + "\" " + mail_to);
		System.out.println(printOut());
		put("Subject: " + object_to);
		System.out.println(printOut());
		put(text_mail);
		System.out.println(printOut());
		put("QUIT");
		System.out.println(printOut());
	}

	private void put(String ans) throws IOException, InterruptedException
	{
		
			Thread.sleep(100);
		
		out.write((ans + "\n").getBytes(Charset.forName("UTF-8")));
		out.flush();
		System.out.println(ans);
	}

	

	public static void main(String[] args)
	{
		if (args.length != 3)
			System.err.println("No arguments!");
		try
		{
			new TukMail(args);
		} catch (IOException | InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String printOut()
	{
		String out_scan = "";
		for (int i = 0; i < 100; i++)
		{
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			String gout = ((Scan) scan).getOut();
			if (gout.equals(out_scan) && !gout.equals(""))
				break;
			out_scan = ((Scan) scan).getOut();
		}
		((Scan) scan).clearOut();
		return out_scan;
	}
}
