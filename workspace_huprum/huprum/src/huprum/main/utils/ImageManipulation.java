package huprum.main.utils;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class ImageManipulation
{
	private BufferedImage    image;
	private String           base64String;
	private String           fileType;
	private FileOutputStream imageOutFile;

	public void readFile(File file) throws IOException
	{
		fileType = Files.probeContentType(file.toPath());
		base64String = Base64Image.encoder(file);
		byte[] btDataFile = Base64.getDecoder().decode(base64String);
		image = ImageIO.read(new ByteArrayInputStream(btDataFile));
	}

	public ImageManipulation(String base64) throws IOException
	{
		base64String = base64.split(",")[1];
		fileType = base64.split(",")[0].split(";")[0].split(":")[1];
		byte[] btDataFile = Base64.getDecoder().decode(base64String);
		image = ImageIO.read(new ByteArrayInputStream(btDataFile));
	}

	public ImageManipulation(Component main) throws IOException
	{
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Найдите файл изображения: ");
		jfc.setAcceptAllFileFilterUsed(false);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Изображения" , "jpg","jpeg","png","gif","bmp");
		jfc.addChoosableFileFilter(filter);
		int returnValue = jfc.showOpenDialog(main);
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			File   selectedFile = jfc.getSelectedFile();
			readFile(selectedFile);
		}
	}

	public int getHeight()
	{
		return image.getHeight();
	}

	public String getFileType()
	{
		return fileType;
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
		return "BufferedImage: Width = " + getWidth() + ", Height = " + getHeight() + ", Type = " + getFileType();
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

//data:image/png;base64, iV
	public String getBase64()
	{
		return "data:" + fileType + ";base64," + base64String;
	}

	public void save(Component main) throws IOException
	{
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Найдите директорию для сохранения вашего файла: ");
		jfc.setAcceptAllFileFilterUsed(false);
		String                  ext    = fileType.split("/")[1];
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Изображение типа ." + ext, ext);
		jfc.addChoosableFileFilter(filter);
		int returnValue = jfc.showSaveDialog(main);
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			File   selectedFile = jfc.getSelectedFile();
			String img_file     = checkFileName(selectedFile, fileType);
			File   f            = new File(img_file);
			if (f.exists() && f.isFile())
			{
				int confirm = JOptionPane.showConfirmDialog(main,
						"Файл " + f.getName() + " существует. Вы готовы перезаписать его?", "Предупреждение!",
						JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);
				if (confirm != JOptionPane.YES_OPTION)
					return;
			}
			saveImage(img_file);
		}
	}

	public void saveImage(String pathFile) throws IOException
	{
		imageOutFile = new FileOutputStream(pathFile);
		byte[] imageByteArray = Base64.getDecoder().decode(base64String);
		imageOutFile.write(imageByteArray);
	}

	private String checkFileName(File f, String fileType)
	{
		String types[][] = new String[][]
		{
				{ "image/jpeg", "jpg" },
				{ "image/jpeg", "jpeg" },
				{ "image/png", "png" },
				{ "image/x-ms-bmp", "bmp" },
				{ "image/gif", "gif" } };
		String ext       = null;
		for (int i = 0; i < types.length; i++)
			if (types[i][0].equals(fileType))
			{
				ext = types[i][1];
				break;
			}
		String path = f.toPath().toString();
		int    li   = path.lastIndexOf('.');
		if (li <= 0)
			return path + "." + ext;
		return path.substring(0, li) + "." + ext;
	}
}
