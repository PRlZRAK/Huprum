package huprum.main.toolbar;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import huprum.main.Huprum;

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
		
	}

	public class StopActionList implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			main.configSave();
			main.userLogoff();
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
				main.configSave();
				main.userLogoff();
				try
				{
					Runtime.getRuntime().exec("java -jar huprum.jar");
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				System.exit(0);
			}
		}
	}
}
