package huprum.main.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import huprum.main.Huprum;
import huprum.main.utils.Lang;
import huprum.main.utils.Utilit;

public class ToolBar extends JToolBar
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton           addUser;
	private JButton           back;
	private JButton           removeUser;
	private Huprum            main;
	private JButton           setLang;
	private BufferedImage     engIcon;
	private BufferedImage     rusIcon;
	private JButton           profile;
	private JButton           settings;

	public ToolBar(Huprum main)
	{
		this.main = main;
		// кнопка выхода из аккаунта
		back = new JButton();
		BufferedImage backIcon = null;
		try
		{
			backIcon = ImageIO.read(new URL(Utilit.IMG_URL + "back.png"));
			back.setIcon(new ImageIcon(backIcon));
		} catch (IOException e)
		{
			back.setText("exit");
		}
		back.addActionListener(new BackActionList1());
		back.setToolTipText(Lang.put("Exit#Выход"));
		add(back);
		// кнопка закрытия
		JButton       stop     = new JButton();
		BufferedImage stopIcon = null;
		try
		{
			stopIcon = ImageIO.read(new URL(Utilit.IMG_URL + "stop.png"));
			stop.setIcon(new ImageIcon(stopIcon));
		} catch (IOException e)
		{
			stop.setText("Stop");
		}
		stop.addActionListener(new StopActionList());
		stop.setToolTipText(Lang.put("End of work#Конец работы"));
		add(stop);
		// кнопка удаления переписок
		removeUser = new JButton();
		BufferedImage removeUserIcon = null;
		try
		{
			removeUserIcon = ImageIO.read(new URL(Utilit.IMG_URL + "remove_user.png"));
			removeUser.setIcon(new ImageIcon(removeUserIcon));
		} catch (IOException e)
		{
			removeUser.setText("remove_user");
		}
		removeUser.addActionListener(new RemoveUserActionList(main));
		removeUser.setToolTipText(Lang.put("Delete selected chat#Удалить выбранный чат"));
		add(removeUser);
		// кнопка добавления чатов
		addUser = new JButton();
		BufferedImage addUserIcon = null;
		try
		{
			addUserIcon = ImageIO.read(new URL(Utilit.IMG_URL + "add_user.png"));
			addUser.setIcon(new ImageIcon(addUserIcon));
		} catch (IOException e)
		{
			addUser.setText("add_user");
		}
		addUser.addActionListener(new AddUserActionList(main));
		addUser.setToolTipText(Lang.put("Find, add a chat with a user by login or mail or phone#"
				+ "Найти, добавить чат с юзером по логину или майлу или телефону"));
		add(addUser);
		//
		setLang = new JButton();
		try
		{
			engIcon = ImageIO.read(new URL(Utilit.IMG_URL + "eng.png"));
			rusIcon = ImageIO.read(new URL(Utilit.IMG_URL + "rus.png"));
			if (Lang.getLang().equals("ru"))
				setLang.setIcon(new ImageIcon(engIcon));
			else
				setLang.setIcon(new ImageIcon(rusIcon));
		} catch (IOException e1)
		{
			setLang.setText("eng_rus");
		}
		setLang.addActionListener(new langActionList(this));
		setLang.setToolTipText(Lang.put("Сменить язык#Switch language"));
		add(setLang);
		//
		profile = new JButton();
		BufferedImage profileIcon = null;
		try
		{
			profileIcon = ImageIO.read(new URL(Utilit.IMG_URL + "profile.png"));
			profile.setIcon(new ImageIcon(profileIcon));
		} catch (IOException e)
		{
			profile.setText("profile");
		}
		profile.addActionListener(new РrofileActionList());
		profile.setToolTipText(Lang.put("Profile#Профиль"));
		add(profile);
		//
		settings = new JButton();
		BufferedImage settingsIcon = null;
		try
		{
			settingsIcon = ImageIO.read(new URL(Utilit.IMG_URL + "settings.png"));
			settings.setIcon(new ImageIcon(settingsIcon));
		} catch (IOException e)
		{
			settings.setText("Settings");
		}
		settings.addActionListener(new SettingsActionList());
		settings.setToolTipText(Lang.put("Settings#Настройки"));
		add(settings);
	}

	public class SettingsActionList implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JScrollPane scsetp = main.getLoginer().getChat().scsetp;
			changerp(scsetp);
		}
	}

	public class РrofileActionList implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			JScrollPane sep = main.getLoginer().getChat().sep;
			changerp(sep);
		}
	}

	private void changerp(JScrollPane panel)
	{
		if (panel.isVisible())
			panel.setVisible(false);
		else
		{
			main.getLoginer().getChat().lastrp.setVisible(false);
			panel.setVisible(true);
			main.getLoginer().getChat().lastrp = panel;
		}
		main.revalidate();
		main.repaint();
	}

	private void breakoff()
	{
		main.configSave();
		main.userLogoff();
		main.store.close();
	}

	public class langActionList implements ActionListener
	{
		private ToolBar toolBar;

		public langActionList(ToolBar toolBar)
		{
			this.toolBar = toolBar;
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if (Lang.getLang().equals("ru"))
			{
				Lang.setEn();
				toolBar.setLang.setIcon(new ImageIcon(toolBar.rusIcon));
			} else
			{
				Lang.setRu();
				toolBar.setLang.setIcon(new ImageIcon(toolBar.engIcon));
			}
			int confirm = JOptionPane.showConfirmDialog(main,
					Lang.put("If you want to immediately change the interface language, click Yes"
							+ "#Если вы хотите немедленной смены языка интерфейса, нажмите Yes"),
					Lang.put("Switch language#Смена языка"), JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (confirm == JOptionPane.YES_OPTION)
			{
				breakoff();
				try
				{
					Runtime.getRuntime().exec("java -jar tuktuk.jar");
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				System.exit(0);
			}
		}
	}

	public class StopActionList implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			breakoff();
			System.exit(0);
		}
	}

	public class BackActionList1 implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			int confirm = JOptionPane.showConfirmDialog(main, "Вы уверены, что хотите выйти?", "Выход из аккаунта",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (confirm == JOptionPane.YES_OPTION)
			{
				breakoff();
				try
				{
					Runtime.getRuntime().exec("java -jar tuktuk.jar");
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				System.exit(0);
			}
		}
	}
}
