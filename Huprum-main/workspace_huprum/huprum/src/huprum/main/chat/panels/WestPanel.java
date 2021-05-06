package huprum.main.chat.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import huprum.main.chat.Chat;

public class WestPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridBagConstraints c;
	public WestPanel(Chat main) {
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.weightx = 1;
		c.anchor = GridBagConstraints.NORTHEAST;
		c.insets = new Insets(10, 10, 10, 10);
		JLabel l = new JLabel();
		l.setPreferredSize(new Dimension(300,0));
		add(l);
		c.gridx = 0;
		c.gridy = 0;
		
		
}
}