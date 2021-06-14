package huprum.main.toolbar;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import huprum.main.Huprum;

public class ToolBar extends JToolBar{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton addUser;

public ToolBar(Huprum main) {		
		addUser = new JButton();
		ImageIcon addUserButIcon = new ImageIcon(
		Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/add user.png")));
		addUser.setIcon(addUserButIcon);
		addUser.addActionListener(new AddUserActionList(main));
		add(addUser);		
	}
}
