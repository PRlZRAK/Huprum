package huprum.main.img;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import huprum.main.smile.Smile;
import huprum.main.utils.Utilit;
import net.coobird.thumbnailator.Thumbnails;

/**
 * @author yaa Класс для манипулирования изображениями удобный для swing
 *         интерфейса
 * 
 */
public class ImageManipulation
{
	private BufferedImage    image = null;
	private String           base64String;
	private String           fileType;
	private FileOutputStream imageOutFile;
	private FileInputStream  imageInFile;
	private int              id;
	private String           file_name;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * @param base64
	 * @throws IOException конструктор для изображения полученного с сервера, где
	 *                     оно хранилось в виде base64 строки
	 */
	public ImageManipulation(String base64) throws IOException
	{
		base64String = base64.split(",")[1];
		fileType = base64.split(",")[0].split(";")[0].split(":")[1];
		byte[] btDataFile = Base64.getDecoder().decode(base64String);
		image = ImageIO.read(new ByteArrayInputStream(btDataFile));
	}

	/**
	 * @param main
	 * @throws IOException конструктор для получения изображения их файловой
	 *                     сиистемы юзера для отправки на сервер
	 */
	public ImageManipulation(Component main) throws IOException
	{
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Найдите файл изображения: ");
		jfc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Изображения", "jpg", "jpeg", "png", "gif", "bmp");
		jfc.addChoosableFileFilter(filter);
		int returnValue = jfc.showOpenDialog(main);
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = jfc.getSelectedFile();
			readFile(selectedFile);
		}
	}

	/**
	 * @param file
	 * @throws IOException читает файл изображения и преобразует его в base64
	 */
	public void readFile(File file) throws IOException
	{
		file_name = file.getAbsolutePath();
		fileType = Files.probeContentType(file.toPath());
		base64String = encoder(file);
		byte[] btDataFile = Base64.getDecoder().decode(base64String);
		image = ImageIO.read(new ByteArrayInputStream(btDataFile));
	}

	/**
	 * @return высота изображения в пикселах
	 */
	public int getHeight()
	{
		return image.getHeight();
	}

	/**
	 * @return тип или формат файла
	 */
	public String getFileType()
	{
		return fileType;
	}

	/**
	 * @return ширина изображения в пикселах
	 */
	public int getWidth()
	{
		return image.getWidth();
	}

	/**
	 * @return возвращает полноразмерный ImageIcon для JLabel
	 */
	public ImageIcon getImageIcon()
	{
		return new ImageIcon(image);
	}

	/**
	 * @param maxX максимальная ширина иконки
	 * @param maxY максимальная высота иконки
	 * @return возвращает ImageIcon для JLabel не больше максимальных параметров но
	 *         с сохранением пропорций
	 */
	public ImageIcon getImageIcon(int maxX, int maxY)
	{
		int ns[] = newSize(maxX, maxY);
		return new ImageIcon(imageResize(ns[0], ns[1]));
	}

	/**
	 * для отладки
	 */
	public String toString()
	{
		return "BufferedImage: Width = " + getWidth() + ", Height = " + getHeight() + ", Type = " + getFileType();
	}

	/**
	 * @param x ширина
	 * @param y высота
	 * @return возвращает изображение новых размеров
	 */
	public Image imageResize(int x, int y)
	{
		return image.getScaledInstance(x, y, BufferedImage.SCALE_SMOOTH);
	}

	/**
	 * @param maxX
	 * @param maxY
	 * @return ширину и высоту не больше максимальных значений с сохранением
	 *         пропорций (внутренний метод)
	 */
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

	/**
	 * @return изображение в виде base64 строки
	 */
	public String getBase64()
	{
		return "data:" + fileType + ";base64," + base64String;
	}

	/**
	 * @param main
	 * @throws IOException диалог для сохранения изображения в файловой системе
	 *                     юзера (сохранение присланных картинок)
	 */
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

	/**
	 * @param pathFile полное имя файла
	 * @throws IOException сохраняет изображение в файл
	 */
	public void saveImage(String pathFile) throws IOException
	{
		imageOutFile = new FileOutputStream(pathFile);
		byte[] imageByteArray = Base64.getDecoder().decode(base64String);
		imageOutFile.write(imageByteArray);
	}

	/**
	 * @param f        файл изображения
	 * @param fileType тип/формат файла
	 * @return исправляет префикс файла, если неправильно его ввел (внутренний
	 *         метод)
	 */
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

	/**
	 * @param main   адресс главного окна или null
	 * @param string надпись на изображении. Метод для показа изображения в большом
	 *               размере. Предназначен для обработки события.
	 * @throws IOException
	 */
	public void showSave(Component main, String string) throws IOException
	{
		int i = JOptionPane.showOptionDialog(main, "", string, JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, getImageIcon(), new String[]
				{ "Закрыть", "Сохранить как.." }, "default");
		if (i == JOptionPane.NO_OPTION)
			save(main);
	}

	public void show(Component main, String string) throws IOException
	{
		JOptionPane.showOptionDialog(main, "", string, JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE,
				getImageIcon(), new String[]
				{ "Закрыть" }, "default");
	}

	/**
	 * @param i       максимальная ширина в пикселах
	 * @param j       максимальная высата в пикселах
	 * @param txt     текст под иконкой
	 * @param timestr для вывода времени сообщений, если null не выводится
	 * @param k       максиимальная ширина текста в буквах
	 * @param bgcolor цвет фона
	 * @return панель с изображением и текстом под ним
	 */
	public JPanel getImageTxt(int i, int j, String txt, String timestr, int k, Color bgcolor)
	{
		JPanel panel = new JPanel();
		// panel.setSize(i, j);
		panel.setBackground(bgcolor);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		panel.add(new JLabel(getImageIcon(i, j)), c);
		c.gridy = 1;
		String dt = "";
		if (timestr != null)
		{
			dt = "<p style=\"font-size: 7px;color: " + Utilit.S_COLOR_1057 + ";\">" + timestr;
			if (!txt.trim().equals(""))
				dt = "<br><br>" + dt;
		}
		panel.add(new JLabel("<html><p><br>" + Smile.replace(Utilit.insertWordWrap(txt, k, "<br>")) + "</p>" + dt), c);
		return panel;
	}

	/**
	 * @param file изображение
	 * @return строка в формате base64
	 * @throws IOException читает и перекодирует файл (внутренний метод)
	 */
	private String encoder(File file) throws IOException
	{
		String base64Image = "";
		imageInFile = new FileInputStream(file);
		byte imageData[] = new byte[(int) file.length()];
		imageInFile.read(imageData);
		base64Image = Base64.getEncoder().encodeToString(imageData);
		return base64Image;
	}

	/**
	 * @return BufferedImage объект. Метод для проверки на коректность отработки
	 *         конструктора
	 */
	public BufferedImage getImage()
	{
		return image;
	}

	public void checkMaxSize(int maxX, int maxY) throws IOException
	{
		if (getImage() == null)
			return;
		int x = getWidth();
		int y = getHeight();
		if (x <= maxX || y <= maxY)
			return;
		String ext     = "." + fileType.split("/")[1];
		File   tmpFile = File.createTempFile("tuk", ext);
		Thumbnails.of(new File(file_name)).size(maxX, maxY).toFile(tmpFile);
		readFile(tmpFile);
		tmpFile.deleteOnExit();
	}
}
