package huprum.main.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * @author yaa Два статических метода для преобразования файла изображения в
 *         Base64 и обратно
 *
 */
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
	public static void main(String[] args)
	{
		String buf;
		try
		{
			buf = Base64Image.encoder(new File("logo_right.png"));
			System.out.println(buf);
			byte[]        btDataFile = Base64.getDecoder().decode(buf);
			BufferedImage image      = ImageIO.read(new ByteArrayInputStream(btDataFile));
			JOptionPane.showMessageDialog(null, "", "Image", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}