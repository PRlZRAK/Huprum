package huprum.main.chat.panels;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import huprum.main.Huprum;
import huprum.main.img.TukPanel;
import huprum.main.utils.Lang;
import huprum.main.utils.Utilit;

public class SettingsPanel extends TukPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Huprum            main;

	public SettingsPanel(Huprum main) throws IOException
	{
		super("logo_en.png", 100, 100, 16, 12, Utilit.COLOR_1085);
		this.main = main;
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 20, 5, 20);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		String logo_name;
		if (Lang.getLang().equals("ru"))
			logo_name = "logo_ru.png";
		else
			logo_name = "logo_en.png";
		super.setImg(logo_name);
		c.gridx = 1;
		ImageIcon close_image;
		JLabel    label_close = new JLabel();
		try
		{
			close_image = new ImageIcon(ImageIO.read(new URL(Utilit.IMG_URL + "close.png")));
			label_close.setIcon(close_image);
		} catch (IOException e)
		{
			label_close.setText("X");
		}
		label_close.addMouseListener(new CloseWidow());
		label_close.setToolTipText(Lang.put("Close a window#Закрыть окно"));
		add(label_close, c);
		c.gridx = 0;
		c.gridy++;
		add(new JLabel("<html><h2 >" + Lang.put("Settings#Настройки") + ":"), c);
		c.gridy++;
		c.gridx = 0;
		add(new JLabel(Lang.put("Show woodpecker:#Показывать дятла:")), c);
		c.gridx = 1;
		JCheckBox dyatel_check = new JCheckBox();
		dyatel_check.setSelected(Utilit.SET_DYATEL_SHOW);
		dyatel_check.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Utilit.SET_DYATEL_SHOW = dyatel_check.isSelected();
				main.getLoginer().getChat().dp.setVisible(Utilit.SET_DYATEL_SHOW);
			}
		});
		add(dyatel_check, c);
		//
		c.gridy++;
		c.gridx = 0;
		add(new JLabel(Lang.put("Woodpecker knocking:#Стук дятла:")), c);
		c.gridx = 1;
		JCheckBox stuk_check = new JCheckBox();
		stuk_check.setSelected(Utilit.SET_STUK_SOUND);
		stuk_check.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Utilit.SET_STUK_SOUND = stuk_check.isSelected();
			}
		});
		add(stuk_check, c);
		//
		c.gridy++;
		c.gridx = 0;
		add(new JLabel(Lang.put("Woodpecker cry:#Крик дятла:")), c);
		c.gridx = 1;
		JCheckBox cry_check = new JCheckBox();
		cry_check.setSelected(Utilit.SET_CRY_SOUND);
		cry_check.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Utilit.SET_CRY_SOUND = cry_check.isSelected();
			}
		});
		add(cry_check, c);
		//
		c.gridy++;
		c.gridx = 0;
		add(new JLabel(Lang.put("Message sending sound:#Звук отправки сообщения:")), c);
		c.gridx = 1;
		JCheckBox send_check = new JCheckBox();
		send_check.setSelected(Utilit.SET_SEND_SOUND);
		send_check.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Utilit.SET_SEND_SOUND = send_check.isSelected();
			}
		});
		add(send_check, c);
	}

	public class CloseWidow implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			JScrollPane scsetp = main.getLoginer().getChat().scsetp;
			if (scsetp.isVisible())
				scsetp.setVisible(false);
			else
				scsetp.setVisible(true);
			main.revalidate();
			main.repaint();
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
}
