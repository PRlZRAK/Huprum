package huprum.main.chat.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONObject;

import huprum.main.Huprum;

import huprum.main.utils.ImageManipulation;
import huprum.main.utils.Utilit;

public class EastPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2864670554727813946L;
	private JSONObject        personal_data;
	private String            avatar;
	private JLabel            label_icon;

	public EastPanel(Huprum main) throws IOException
	{
		personal_data = main.getPersonalData();
		// avatar
		ImageIcon icon;
		if (personal_data.isNull("avatar"))
		{
			avatar = null;
			icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/user.png")));
		} else
		{
			avatar = personal_data.getString("avatar");
			ImageManipulation im = new ImageManipulation(avatar);
			icon = im.getImageIcon(100, 100);
		}
		ImageIcon pensil_image = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/pensil.png")));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(50, 10, 10, 10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		add(new JLabel("<html><p >Профиль"), c);
		c.gridy++;
		c.gridx = 1;
		label_icon = new JLabel(icon);
		add(label_icon, c);
		c.gridx = 2;
		JLabel pensil1 = new JLabel(pensil_image);
		pensil1.addMouseListener(new FaceEditList(this));
		add(pensil1, c);
	}

	public class FaceEditList implements MouseListener
	{
		private EastPanel eastPanel;

		public FaceEditList(EastPanel eastPanel)
		{
			this.eastPanel = eastPanel;
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			ImageManipulation im;
			try
			{
				im = new ImageManipulation(eastPanel);
				label_icon.setIcon(im.getImageIcon(100, 100));
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
