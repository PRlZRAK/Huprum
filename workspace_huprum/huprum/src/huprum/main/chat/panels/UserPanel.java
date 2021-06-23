package huprum.main.chat.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.connections.Client;
import huprum.main.img.ImageManipulation;
import huprum.main.utils.Utilit;

public class UserPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long  serialVersionUID = 4745556330398055150L;
	private Client             cl;
	private JLabel             label_icon;
	private GridBagConstraints c;
	private boolean            show;
	private ImageManipulation  im;

	public UserPanel(Huprum main)
	{
		setLayout(new GridBagLayout());
		setBackground(Utilit.COLOR_389);
		c = new GridBagConstraints();
		c.insets = new Insets(5, 10, 5, 10);
		c.fill = GridBagConstraints.HORIZONTAL;
		cl = main.getCl();
	}

	public void redr(int id)
	{
		Map<String, String> pars = new HashMap<String, String>();
		pars.put("action", "get_user");
		pars.put("id", id + "");
		String otvet = null;
		try
		{
			otvet = cl.send(pars);
		} catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		JSONObject jo = new JSONObject(otvet);
		removeAll();
		c.gridx = 0;
		c.gridy = 0;
		ImageIcon icon;
		if (jo.isNull("avatar"))
		{
			icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/mask.jpg")));
			show = false;
		} else
		{
			String avatar = jo.getString("avatar");
			im = null;
			try
			{
				im = new ImageManipulation(avatar);
				icon = im.getImageIcon(100, 100);
			} catch (IOException e)
			{
				icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/user.png")));
				e.printStackTrace();
			}
			show = true;
		}
		add(new JLabel(jo.getString("login")),c);
		c.gridy++;
		label_icon = new JLabel(icon);
		label_icon.addMouseListener(new Show(this));
		add(label_icon, c);
		c.gridy++;
		if (!jo.isNull("fio"))
			add(new JLabel("<html>" + Utilit.InsertPerenos(jo.getString("fio"), 20, "<br>")), c);
		updateUI();
	}

	public class Show implements MouseListener
	{
		private UserPanel main;

		public Show(UserPanel userPanel)
		{
			this.main = userPanel;
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			if (show)
				try
				{
					im.show(main, "", im);
				} catch (IOException e)
				{
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
