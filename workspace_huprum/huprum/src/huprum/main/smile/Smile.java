package huprum.main.smile;

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
			String txt =southPanel.vod.getText();
			txt = Utilit.insertWordWrap(txt, 50, "<br>");
			txt = Smile.replace(txt);
			southPanel.show_label.setText("<html>" + txt);
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
			{ ")))", "https://i.ibb.co/Yb5CjVc/Grinning-face-1f600.png" },
			{ "=)", "https://i.ibb.co/M9nvDdB/Face-with-tears-of-joy-1f602.png" },
			{ ":x", "https://i.ibb.co/4pFb4dq/Shushing-face-1f92b.png" },
			{ "-_-", "https://i.ibb.co/crBDyjW/Expressionless-face-1f611.png" },
			{ "B)", "https://i.ibb.co/3CgZRBy/Smiling-face-with-sunglasses-1f60e.png" },
			{ ":(", "https://i.ibb.co/KGq4Tv0/Worried-face-1f61f.png" },
			{ ":o", "https://i.ibb.co/LJB2dJr/Hushed-face-1f62f.png" },
			{ "*_*", "https://i.ibb.co/bKFxpNn/Flushed-face-1f633.png" },
			{ ":_(", "https://i.ibb.co/hRmRCDb/Loudly-crying-face-1f62d.png" },
			{ "0x0", "https://i.ibb.co/Phpwgz1/Yawning-face-1f971.png" },
			{ "8)", "https://i.ibb.co/nwdcGFL/Pouting-face-1f621.png" },
			{ ":)", "https://emoticons.do.am/1/a2/5.gif" },
			{ ":-(", "https://emoticons.do.am/1/a2/13.gif" },
			{ ":D", "https://emoticons.do.am/1/a2/9.gif" },
			{ "]:->", "https://emoticons.do.am/1/a2/17.gif" },
			{ ";)", "https://emoticons.do.am/1/a2/21.gif" },
			{ ":-||", "https://emoticons.do.am/1/a2/15.gif" },
			{ ":-&", "https://emoticons.do.am/1/91/29.gif" },
			{ "(Y)", "https://emoticons.do.am/1/91/34.gif" },
			{ ":-/", "https://emoticons.do.am/1/91/51.gif" },
			{ ":-?", "https://emoticons.do.am/1/91/25.gif" },
			{ ":-<", "https://emoticons.do.am/1/91/97.gif" },
			{ ":y:", "https://emoticons.do.am/1/91/110.gif" }, };

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
		return "<img src=\"" + file_name + "\" height=" + height + " width=" + width + " >";
	}

	public static String tag(String file_name)
	{
		return "<img src=\"" + file_name + "\"  >";
	}
}
