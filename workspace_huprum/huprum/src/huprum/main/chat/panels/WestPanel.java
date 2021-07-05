package huprum.main.chat.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.chat.UserButtomClass;
import huprum.main.connections.Client;
import huprum.main.img.ImageManipulation;
import huprum.main.loginer.Loginer;
import huprum.main.media.PlaySound;
import huprum.main.utils.Utilit;

public class WestPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long  serialVersionUID = 1L;
	private GridBagConstraints c;
	private Client             cl;
	private Huprum             main;
	private UserButtomClass[]  batArray;
	private int                myId;
	private UserButtomClass    lastButton;
	private Loginer            loginer;
	private String             sId;
	private String             strMyId;
	private String             strId            = null;
	private int                ksum             = -1;
	private PlaySound          clip;

	public String getStrId()
	{
		return sId;
	}

	public WestPanel(Huprum main)
	{
		this.main = main;
		loginer = main.getLoginer();
		cl = main.getCl();
		myId = loginer.getId();
		// System.out.println(myId);
		lastButton = new UserButtomClass("");
		setLayout(new GridBagLayout());
		setBackground(Utilit.COLOR_490);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTH;
		try
		{
			clip = new PlaySound("5216_pod-zvonok.ru__.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		redr();
	}

	/*
	 * перерисовка панели
	 */
	public void redr()
	{
		Map<String, String> pars = new HashMap<String, String>();
		pars.put("action", "get_my_users");
		pars.put("id", Integer.toString(myId));
		pars.put("ksum", Integer.toString(ksum));
		String otvet = null;
		try
		{
			otvet = cl.send(pars);
		} catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		JSONObject jo1    = new JSONObject(otvet);
		int        status = (int) jo1.get("status");
		if (status == 0)
		{
			ksum = (int) jo1.get("ksum");
			removeAll();
			c.gridx = 0;
			c.gridy = 0;
			ActionListener userButtonListener = new UserButtonListener();
			JSONArray      ja                 = jo1.getJSONArray("users");
			batArray = new UserButtomClass[ja.length()];
			for (int i = 0; i < ja.length(); i++)
			{
				JSONObject jo = ja.getJSONObject(i);
				int        id = Integer.parseInt(jo.getString("id"));
				
				try
				{
					
					ImageManipulation im = main.store.getAvaImg(id);
					if (im != null)
					{
						c.gridx = 0;
						ImageIcon icon       = im.getImageIcon(32, 32);
						JLabel    label_icon = new JLabel(icon);
						label_icon.addMouseListener(new Show1(this, im));
						add(label_icon, c);
					}
				} catch (SQLException | IOException e)
				{
					
					e.printStackTrace();
				}
				
				String login = (String) jo.get("login");
				
				batArray[i] = new UserButtomClass(login, Utilit.COLOR_1074, Color.white);
				batArray[i].setId(id);
				if (strId != null && strId.equals(id + ""))
					batArray[i].setSelect(true);
				else
					batArray[i].setSelect(false);
				batArray[i].addActionListener(userButtonListener);
				c.gridx = 1;
				add(batArray[i], c);
				int cnt    = jo.getInt("cnt");
				int online = jo.getInt("online");
				if (cnt + online > 0)
				{
					c.gridx = 2;
					String img     = null;
					String tooltip = null;
					if (cnt > 0 && online == 0)
					{
						img = "<html><div style=\"background-image: url(" + Utilit.HUPRUM_URL
								+ "server/img/orang_cirk_.png);background-repeat: no-repeat; color: white;  \">&nbsp;"
								+ cnt + "&nbsp;&nbsp;";
						tooltip = "новые сообщения";
					} else if (cnt == 0 && online > 0)
					{
						img = "<html><div style=\"background-image: url(" + Utilit.HUPRUM_URL
								+ "server/img/blue_cirk_.png);background-repeat: no-repeat;  \">&nbsp;&nbsp;&nbsp;&nbsp;";
						tooltip = "юзер онлайн";
					} else if (cnt > 0 && online > 0)
					{
						img = "<html><div style=\"background-image: url(" + Utilit.HUPRUM_URL
								+ "server/img/all_cirk_.png);background-repeat: no-repeat; color: white; \">&nbsp;"
								+ cnt + "&nbsp;&nbsp;";
						tooltip = "новые сообщения + юзер онлайн";
					}
					JLabel new_msg = new JLabel(img);
					new_msg.setOpaque(false);
					new_msg.setToolTipText(tooltip);
					add(new_msg, c);
					c.gridx = 0;
				}
				c.gridy++;
			}
			
			main.revalidate();
			main.repaint();
			
			clip.play();
		}
	}

	public class UserButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			lastButton.setSelect(false);
			UserButtomClass button = (UserButtomClass) (e.getSource());
			button.setSelect(true);
			strMyId = Integer.toString(myId);
			strId = button.getId().toString();
			loginer.getChat().cp.last_id = "0";
			lastButton = button;
			main.getLoginer().getChat().sp.setImgNull();
			main.getLoginer().getChat().up.redr(button.getId());
		}
	}

	public String getMyId()
	{
		return strMyId;
	}

	public String getId()
	{
		return strId;
	}

	public UserButtomClass getLastButton()
	{
		return lastButton;
	}

	public class Show1 implements MouseListener
	{
		private WestPanel         main;
		private ImageManipulation im;

		public Show1(WestPanel westPanel, ImageManipulation im)
		{
			this.main = westPanel;
			this.im = im;
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			try
			{
				im.show(main, "");
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