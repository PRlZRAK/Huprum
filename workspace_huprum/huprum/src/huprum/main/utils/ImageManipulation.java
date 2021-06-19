package huprum.main.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageManipulation
{
	private BufferedImage image;
	private String        base64String;

	public ImageManipulation(File file) throws IOException
	{
		base64String = Base64Image.encoder(file);
		byte[] btDataFile = Base64.getDecoder().decode(base64String);
		image = ImageIO.read(new ByteArrayInputStream(btDataFile));
	}

	public ImageManipulation(String base64) throws IOException
	{
		base64String = base64;
		byte[] btDataFile = Base64.getDecoder().decode(base64String);
		image = ImageIO.read(new ByteArrayInputStream(btDataFile));
	}

	public int getHeight()
	{
		return image.getHeight();
	}

	public int getType()
	{
		return image.getType();
	}

	public int getWidth()
	{
		return image.getWidth();
	}

	public ImageIcon getImageIcon()
	{
		return new ImageIcon(image);
	}

	public String toString()
	{
		return "BufferedImage: Width = " + getWidth() + ", Height = " + getHeight() + ", Type = " + getType();
	}

	public ImageIcon getImageIcon(int maxX, int maxY)
	{
		int ns[] = newSize(maxX, maxY);
		return new ImageIcon(imageResize(ns[0], ns[1]));
	}

	public Image imageResize(int x, int y)
	{
		return image.getScaledInstance(x, y, BufferedImage.SCALE_SMOOTH);
	}

	private int[] newSize(int maxX, int maxY)
	{
		int x = getWidth();
		int y = getHeight();
		if (x <= maxX && y <= maxY)
			return new int[]
			{ x, y };
		int x1 = maxX;
		int y1 = x1 * y / x;
		if (x1 <= maxX && y1 <= maxY)
			return new int[]
			{ x1, y1 };
		y1 = maxY;
		x1 = y1 * x / y;
		if (x1 <= maxX && y1 <= maxY)
			return new int[]
			{ x1, y1 };
		return null;
	}

	public String getBase64()
	{
		return base64String;
	}
}
