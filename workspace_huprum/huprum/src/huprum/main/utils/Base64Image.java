package huprum.main.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Base64Image
{
	private static FileInputStream  imageInFile;
	private static FileOutputStream imageOutFile;

	public static String encoder(File file) throws IOException
	{
		String base64Image = "";
		imageInFile = new FileInputStream(file);
		byte imageData[] = new byte[(int) file.length()];
		imageInFile.read(imageData);
		base64Image = Base64.getEncoder().encodeToString(imageData);
		return base64Image;
	}

	public static void decoder(String base64Image, String pathFile) throws IOException
	{
		imageOutFile = new FileOutputStream(pathFile);
		byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
		imageOutFile.write(imageByteArray);
	}
}