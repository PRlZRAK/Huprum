package huprum.main.panels;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import huprum.main.chat.Chat;

public class CenterPanel extends JPanel 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridBagConstraints c;
	
	public CenterPanel(Chat main) {
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(1, 10, 1, 10);


	
		
		
		
	}

}
