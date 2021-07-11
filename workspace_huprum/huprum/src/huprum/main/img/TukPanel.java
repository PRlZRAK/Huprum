package huprum.main.img;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import huprum.main.utils.Utilit;
import net.coobird.thumbnailator.Thumbnails;

public class TukPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4271566089630832804L;
	private BufferedImage     img;
	private int               X;
	private int               Y;

	public TukPanel(String im_name, int maxX, int maxY, int x, int y, Color color) throws IOException
	{
		this.X = x;
		this.Y = y;
		if (color != null)
			setBackground(color);
		img = ImageIO.read(TukPanel.class.getResource(im_name));
		int[] size = newSize(img, maxX, maxY);
		if (img.getWidth() == size[0] || img.getHeight() == size[1])
			return;
		img = Thumbnails.of(img).size(size[0], size[1]).asBufferedImage();
	}

	public static int[] newSize(BufferedImage bimp, int maxX, int maxY)
	{
		int x = bimp.getWidth();
		int y = bimp.getHeight();
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

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, X, Y, this);
	}

	public static void main(String[] args)
	{
		JFrame jf = new JFrame("test");
		jf.setSize(600, 500);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TukPanel panel;
		try
		{
			panel = new TukPanel("brand_ru.png", 100, 100, 10, 10, Utilit.COLOR_1085);
			jf.add(panel);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jf.setVisible(true);
	}

	public void setImg(String im_name)
	{
		try
		{
			img = ImageIO.read(TukPanel.class.getResource(im_name));
			revalidate();
			repaint();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}