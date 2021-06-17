package huprum.main.toolbar;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import huprum.main.Huprum;

public class ToolBar extends JToolBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton addUser;
	private JButton back;

	public ToolBar(Huprum main) {

		back = new JButton();
		ImageIcon backIcon = new ImageIcon(
		Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/back 1.png")));
		back.setIcon(backIcon);
		back.addActionListener(new BackActionList(main));
		back.setToolTipText("Выход");
		add(back);

		addUser = new JButton();
		ImageIcon addUserButIcon = new ImageIcon(
		Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/add user.png")));
		addUser.setIcon(addUserButIcon);
		addUser.addActionListener(new AddUserActionList(main));
		addUser.setToolTipText("Найти, добавить чат с юзером по логину или майлу или телефону");
		add(addUser);
	}
}
