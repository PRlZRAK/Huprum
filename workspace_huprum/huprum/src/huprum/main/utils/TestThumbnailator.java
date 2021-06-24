package huprum.main.utils;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class TestThumbnailator
{
	public static void main(String[] args)
	{
		try
		{
			Thumbnails.of(new File("IMG_5837.JPG"))
			.size(160, 160)
			.toFile(new File("thumbnail.jpg"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
