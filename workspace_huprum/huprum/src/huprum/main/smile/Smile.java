package huprum.main.smile;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import huprum.main.utils.Utilit;

public class Smile
{
	private static String smile_array[][] =
	{
			{ ":)", "sm1.png" },
			{ ";-D", "123" } };

	public static void main(String[] args)
	{
		int    DEFAULT_WIDTH  = 400;
		int    DEFAULT_HEIGHT = 300;
		JFrame jframe         = new JFrame("test Utilit");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		Toolkit   kit        = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		jframe.setLocation((screenSize.width - DEFAULT_WIDTH) / 2, (screenSize.height - DEFAULT_HEIGHT) / 2);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		String smile_str = "    :)       ;-D                   ";
		String str_new   = Smile.replace(smile_str);
		JLabel jb        = new JLabel("<html>" + smile_str);
		panel.add(jb);
		JLabel jb1 = new JLabel("<html>" + str_new);
		panel.add(jb1);
		jframe.getContentPane().add(panel);
		jframe.setVisible(true);
	}

	private static String replace(String str)
	{
		for (int i = 0; i < smile_array.length; i++)
		{
			String[] row = smile_array[i];
			while (str.indexOf(row[0]) >= 0)
				str = str.replace(row[0],"<img src=\""+Utilit.SMILE_URL+ row[1]+"\" height=\"16\" width=\"16\" >");
		}
		return str;
	}
}
