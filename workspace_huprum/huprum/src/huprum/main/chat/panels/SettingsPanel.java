package huprum.main.chat.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import huprum.main.Huprum;
import huprum.main.utils.Lang;

public class SettingsPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Huprum main;
	
	
	public SettingsPanel(Huprum main)
	{
		this.main = main;
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 5, 15, 5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		add(new JLabel("<html><h2 >" + Lang.put("Settings#Настройки")), c);
		c.gridwidth = 1;
		c.gridx = 3;
		ImageIcon close_image = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/close.png")));
		JLabel    label_close = new JLabel(close_image);
		label_close.addMouseListener(new CloseWidow());
		add(label_close, c);
		c.gridwidth = 1;
		c.gridy++;
		c.gridx = 0;
	}
	public class CloseWidow implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			JScrollPane scsetp = main.getLoginer().getChat().scsetp;
			if (scsetp.isVisible())
				scsetp.setVisible(false);
			else
				scsetp.setVisible(true);
			main.revalidate();
			main.repaint();
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
		}
	}
}
