package huprum.main.toolbar;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import huprum.main.Huprum;

public class personalAreaActionList extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5422976816726912066L;
	private static final int  DEFAULT_HEIGHT   = 600;
	private static final int  DEFAULT_WIDTH    = 600;

	public personalAreaActionList(Huprum main, ImageIcon personalAreaIcon)
	{
		super("Личный кабинет");
		setSize(800, 600);
		setLocation((main.getWidth() - DEFAULT_WIDTH) / 2, (main.getHeight() - DEFAULT_HEIGHT) / 2);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		setVisible(true);
	}
}
