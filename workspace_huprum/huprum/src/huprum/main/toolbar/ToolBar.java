package huprum.main.toolbar;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import huprum.main.Huprum;
import huprum.main.utils.Lang;

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
	private ImageIcon         engIcon;
	private ImageIcon         rusIcon;
	private JButton profile;

	public ToolBar(Huprum main)
	{
		this.main = main;
		// кнопка выхода из аккаунта
		back = new JButton();
		ImageIcon backIcon = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/back 1.png")));
		back.setIcon(backIcon);
		back.addActionListener(new BackActionList1());
		back.setToolTipText("Выход");
		add(back);
		// кнопка закрытия
		JButton   stop     = new JButton();
		ImageIcon stopIcon = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/stop.png")));
		stop.setIcon(stopIcon);
		stop.addActionListener(new StopActionList());
		stop.setToolTipText("Конец работы");
		add(stop);
		// кнопка удаления переписок
		removeUser = new JButton();
		ImageIcon removeUserIcon = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/remove user.png")));
		removeUser.setIcon(removeUserIcon);
		removeUser.addActionListener(new RemoveUserActionList(main));
		removeUser.setToolTipText("Удалить выбранный чат");
		add(removeUser);
		// кнопка добавления чатов
		addUser = new JButton();
		ImageIcon addUserIcon = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/add user.png")));
		addUser.setIcon(addUserIcon);
		addUser.addActionListener(new AddUserActionList(main));
		addUser.setToolTipText("Найти, добавить чат с юзером по логину или майлу или телефону");
		add(addUser);
		//
		setLang = new JButton();
		engIcon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/eng.png")));
		rusIcon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/rus.png")));
		if (Lang.getLang().equals("ru"))
			setLang.setIcon(engIcon);
		else
			setLang.setIcon(rusIcon);
		setLang.addActionListener(new langActionList(this));
		setLang.setToolTipText(Lang.put("Сменить язык#Switch language"));
		add(setLang);
		//
		profile = new JButton();
		ImageIcon profileIcon = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/profile.png")));
		profile.setIcon(profileIcon);
		profile.addActionListener(new РrofileActionList());
		profile.setToolTipText("Профиль");
		add(profile);
	}
	public class РrofileActionList implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			 JScrollPane sep = main.getLoginer().getChat().sep;
			if(sep.isVisible()) sep.setVisible(false);
			else sep.setVisible(true);
			main.revalidate();
			main.repaint();
		}
	}
	private void breakoff()
	{
		main.configSave();
		main.userLogoff();
		main.store.close();
	}

	public class langActionList implements ActionListener
	{
		// boolean b_lang = true;
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
				toolBar.setLang.setIcon(toolBar.rusIcon);
			} else
			{
				Lang.setRu();
				toolBar.setLang.setIcon(toolBar.engIcon);
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
