package huprum.main.utils;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestImageManipulation extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestImageManipulation(String string) throws IOException
	{
		super(string);
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		add(panel);
		ImageManipulation im = new ImageManipulation(new File("logo_right.png"));
		c.gridx = 0;
		c.gridy = 0;
		panel.add(new JLabel("Характеристики изображения:"),c);
		c.gridx = 1;
		panel.add(new JLabel(im.toString()), c);
		c.gridx = 0;
		c.gridy++;
		panel.add(new JLabel("Оригинальное изображение:"),c);
		c.gridx = 1;
		panel.add(new JLabel(im.getImageIcon()), c);
		c.gridx = 0;
		c.gridy++;
		panel.add(new JLabel("<html>Его измененная пропорционально <br>уменьшенная копия:"),c);
		c.gridx = 1;
		panel.add(new JLabel(im.getImageIcon(100, 100)), c);
		c.gridx = 0;
		c.gridy++;
		panel.add(new JLabel("Первые 50 знаков Base64:"),c);
		c.gridx = 1;
		String base64=im.getBase64();
		panel.add(new JLabel(base64.substring(0, 50)), c);
		ImageManipulation im1 = new ImageManipulation(base64);
		c.gridx = 0;
		c.gridy++;
		panel.add(new JLabel("Изображение из строки:"),c);
		c.gridx = 1;
		panel.add(new JLabel(im1.getImageIcon()), c);
		setVisible(true);
	}

	public static void main(String[] args)
	{
		try
		{
			new TestImageManipulation("test");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
