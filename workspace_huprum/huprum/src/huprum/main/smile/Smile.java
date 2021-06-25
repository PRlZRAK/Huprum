package huprum.main.smile;

import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import huprum.main.chat.panels.SouthPanel;
import huprum.main.utils.Utilit;

public class Smile extends JPanel 
{
	public Smile(SouthPanel southPanel)
	{
		super(new FlowLayout());
		setBackground(Utilit.COLOR_1057);
		for (String[] str_label : smile_array)
		{
			JLabel label = new JLabel("<html>" + tag(str_label[1]));
			label.addMouseListener(new ClickList(southPanel, str_label[0]));
			add(label);
		}
		setVisible(false);
	}

	public class ClickList implements MouseListener
	{
		private SouthPanel southPanel;
		private String     insert_text;

		public ClickList(SouthPanel southPanel, String insert_text)
		{
			this.southPanel = southPanel;
			this.insert_text = insert_text;
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			southPanel.insertText(insert_text);
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7502031030850223394L;
	private static String     smile_array[][]  =
	{
			{ ":)", "4.gif" },
			{ ")))", "5.gif" },
			{ ":(", "21.gif" },
			{ ":-[", "6.gif" },
			{ "D-:", "13.gif" } };

	public static String replace(String str, int width, int height)
	{
		for (int i = 0; i < smile_array.length; i++)
		{
			String[] row = smile_array[i];
			while (str.indexOf(row[0]) >= 0)
				str = str.replace(row[0], tag(row[1], width, height));
		}
		return str;
	}
	public static String replace(String str)
	{
		for (int i = 0; i < smile_array.length; i++)
		{
			String[] row = smile_array[i];
			while (str.indexOf(row[0]) >= 0)
				str = str.replace(row[0], tag(row[1]));
		}
		return str;
	}

	public static String tag(String file_name, int height, int width)
	{
		return "<img src=\"" + Utilit.SMILE_URL + file_name + "\" height=" + height + " width=" + width + " >";
	}

	public static String tag(String file_name)
	{
		return "<img src=\"" + Utilit.SMILE_URL + file_name + "\"  >";
	}
}
