package su.tuktuk.utilit;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class VideoPlayer
{
	private File f;

	public VideoPlayer(URL url) throws IOException
	{
		f = File.createTempFile("test", ".3gp");
		Path path = f.toPath();
		try (InputStream in = url.openStream())
		{
			Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
		}
		f.deleteOnExit();
	}

	private void play()
	{
		try
		{
			Desktop.getDesktop().open(f);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException
	{
		try
		{
			// VideoPlayer p = new VideoPlayer(new
			// URL("https://cloud.mail.ru/public/5xDX/pJUojZADX"));
			VideoPlayer p = new VideoPlayer(new URL("http://tuktuk.su/huprum/server/img/test.3gp"));
			p.play();
			System.out.println("конец");
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
