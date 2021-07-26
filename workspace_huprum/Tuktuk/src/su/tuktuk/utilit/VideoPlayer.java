package su.tuktuk.utilit;

import java.net.MalformedURLException;
import java.net.URL;

public class VideoPlayer
{
	public VideoPlayer(URL url)
	{
		// TODO Auto-generated constructor stub
	}

	private void play()
	{
		// TODO Auto-generated method stub
	}

	public static void main(String[] args)
	{
		try
		{
			VideoPlayer p = new VideoPlayer(new URL(null));
			p.play();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
