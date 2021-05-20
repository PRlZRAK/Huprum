package huprum.main.chat.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import huprum.main.Huprum;
import huprum.main.chat.Chat;

public class CenterPanel extends JPanel 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridBagConstraints c;
	
	public CenterPanel(Huprum main) {
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(1, 10, 1, 10);

		
		
	
		
		
		
	}

}
