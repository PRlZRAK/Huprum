package huprum.main.smile;

import java.awt.Cursor;
import java.awt.Dimension;
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
		super(new FlowLayout(FlowLayout.LEFT));
		setBackground(Utilit.COLOR_1057);
		setPreferredSize(new Dimension(230, 90));
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
			String txt = southPanel.vod.getText();
			txt = Utilit.insertWordWrap(txt, 50, "<br>");
			txt = Smile.replace(txt);
			southPanel.show_label.setText("<html>" + txt);
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
			{ ")))", "Grinning-face-1f600.png" },
			{ "=)", "Face-with-tears-of-joy-1f602.png" },
			{ ":x", "Shushing-face-1f92b.png" },
			{ "-_-", "Expressionless-face-1f611.png" },
			{ "B)", "Smiling-face-with-sunglasses-1f60e.png" },
			{ ":(", "Worried-face-1f61f.png" },
			{ ":o", "Hushed-face-1f62f.png" },
			{ "*_*", "Flushed-face-1f633.png" },
			{ ":_(", "Loudly-crying-face-1f62d.png" },
			{ "0x0", "Yawning-face-1f971.png" },
			{ "8)", "Pouting-face-1f621.png" },
			{ ":)", "5.gif" },
			{ ":-(", "13.gif" },
			{ ":D", "9.gif" },
			{ "]:->", "17.gif" },
			{ ";)", "21.gif" },
			{ ":-||", "15.gif" },
			{ ":-&", "29.gif" },
			{ "(Y)", "34.gif" },
			{ ":-/", "51.gif" },
			{ ":-?", "25.gif" },
			{ ":-<", "97.gif" },
			{ ":y:", "110.gif" }, };

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
