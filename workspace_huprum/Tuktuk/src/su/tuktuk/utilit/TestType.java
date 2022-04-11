package su.tuktuk.utilit;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class TestType
{
	public static void main(String[] args)
	{
		URLConnection urlConnection;
		try
		{
			urlConnection = new URL("https://thumb.cloud.mail.ru/weblink/thumb/xw1/v3vw/2cwVWoQKn").openConnection();
			String mimeType = urlConnection.getContentType(); 
			System.out.println(mimeType);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  

	}
}
