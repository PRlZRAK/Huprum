package huprum.main.panels;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.TextField;

import javax.swing.JPanel;

import huprum.main.chat.Chat;

public class SouthPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SouthPanel(Chat main) {
		TextField vvod = new TextField(90);
		add(vvod);
		Button enter = new Button("Enter");
		add(enter);
		
	}
}
