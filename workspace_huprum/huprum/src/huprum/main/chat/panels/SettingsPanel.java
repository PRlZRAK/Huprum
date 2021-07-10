package huprum.main.chat.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import huprum.main.Huprum;
import huprum.main.img.Mult;
import huprum.main.utils.Lang;
import huprum.main.utils.Utilit;

public class SettingsPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Huprum            main;

	public SettingsPanel(Huprum main)
	{
		this.main = main;
		GridBagLayout gridBagLayout = new GridBagLayout();
		
		setLayout(gridBagLayout);
		setBackground(Utilit.COLOR_1085);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 20, 5, 20);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		try
		{
			String logo_name;
			if (Lang.getLang().equals("ru"))
				logo_name = "logo_ru.png";
			else
				logo_name = "logo_en.png";
			BufferedImage logo = ImageIO.read(Mult.class.getResource(logo_name));
			add(new JLabel(new ImageIcon(logo)), c);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		c.gridx = 1;
		ImageIcon close_image = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/close.png")));
		JLabel    label_close = new JLabel(close_image);
		label_close.addMouseListener(new CloseWidow());
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
}
