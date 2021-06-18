package huprum.main.toolbar;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import huprum.main.Huprum;
import huprum.main.toolbar.ToolBar.BackActionList1;

public class ToolBar extends JToolBar
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton           addUser;
	private JButton           back;
	private Huprum            main;

	public ToolBar(Huprum main)
	{
		this.main = main;
		//
		back = new JButton();
		ImageIcon backIcon = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/back 1.png")));
		back.setIcon(backIcon);
		//back.addActionListener(new BackActionList(main));
		back.addActionListener(new BackActionList1());
		back.setToolTipText("Выход");
		add(back);
		//
		JButton   stop     = new JButton();
		ImageIcon stopIcon = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/stop.png")));
		stop.setIcon(stopIcon);
		stop.addActionListener(new StopActionList());
		stop.setToolTipText("Конец работы");
		add(stop);
		//
		addUser = new JButton();
		ImageIcon addUserButIcon = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/add user.png")));
		addUser.setIcon(addUserButIcon);
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
			main.configSave();
			main.userLogoff();
			try
			{
				Runtime.getRuntime().exec("java -jar huprum.jar");
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
	}

}
