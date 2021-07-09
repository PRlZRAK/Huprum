package huprum.main.chat.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.connections.Client;
import huprum.main.img.ImageManipulation;
import huprum.main.loginer.Loginer;
import huprum.main.media.PlaySound;
import huprum.main.smile.Smile;
import huprum.main.utils.DTime;
import huprum.main.utils.Lang;
import huprum.main.utils.Utilit;

public class CenterPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long       serialVersionUID = 1L;
	private GridBagConstraints      c;
	private int                     myId;
	private Huprum                  main;
	private Loginer                 loginer;
	private Client                  cl;
	private HashMap<String, String> pars;
	private JLabel                  con;
	String                          last_id;
	private PlaySound               clip;
	private int                     day              = 0;
	private int                     show_msg;

	public CenterPanel(Huprum main)
	{
		
		this.main = main;
		loginer = main.getLoginer();
		myId = loginer.getId();
		con = new JLabel("");
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		cl = main.getCl();
		pars = new HashMap<String, String>();
		setBackground(Utilit.COLOR_1068);
		c.insets = new Insets(5, 15, 5, 15);
		c.ipadx = 10;
		try
		{
			clip = new PlaySound("Sound_16487.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			
			e.printStackTrace();
		}
	}

	/*
	 * обновление чата
	 */
	public void chatRedr()
	{
		if (loginer.getChat().wp.getId() == null)
			return;
		/*
		 * обращение к серверу
		 */
		pars.clear();
		pars.put("action", "get_chat");
		pars.put("myid", loginer.getChat().wp.getMyId());
		pars.put("id", loginer.getChat().wp.getId());
		pars.put("last_chat_id", last_id);
		String otvet = null;
		try
		{
			otvet = cl.send(pars);
		} catch (IOException e1)
		{
			c.anchor = GridBagConstraints.SOUTH;
			con.setText("<html><p color=#d3d3d3>Нет соединения с сервером");
			add(con, c);
			return;
		}
		con.setText("");
		JSONObject jo1    = new JSONObject(otvet);
		int        status = (int) jo1.get("status");
		if (status == 0)
		{
			JSONArray jarray = jo1.getJSONArray("chat"); // диагностика
			if (last_id.equals("0"))
			{
				removeAll();
				c.gridwidth = 1;
				for (c.gridx = 0; c.gridx < 3; c.gridx++)
					add(new JLabel("                  "), c);
				c.gridy = 0;
			}
			show_msg = 1; // для вывода новых сообщений
			delNewMsg("Новые сообщения");
			c.gridwidth = 2;
			c.gridy++;
			for (int i = 0; i < jarray.length(); i++)
			{
				JSONObject jo  = jarray.getJSONObject(i);
				String     sId = (String) jo.get("user1_id");
				int        id  = Integer.parseInt(sId);
				DTime      dt  = null;
				c.anchor = GridBagConstraints.CENTER;
				try
				{
					dt = new DTime(jo.getString("dtime"));
					if (dt.day() != day)
					{
						c.gridwidth = 1;
						c.gridx = 1;
						JLabel label_day = new JLabel(dt.dayToString());
						label_day.setForeground(Utilit.COLOR_1057);
						add(label_day, c);
						c.gridwidth = 2;
						c.gridy++;
						day = dt.day();
					}
				} catch (JSONException | ParseException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				 * вывод в ленту заголовка о новых сообщениях
				 */
				int sm = jo.getInt("show_msg");
				if (sm == 0 && show_msg == 1)
				{
					c.gridwidth = 1;
					c.gridx = 1;
					JLabel label_new = new JLabel(Lang.put("New messages#Новые сообщения"));
					label_new.setName("new");
					label_new.setForeground(Utilit.COLOR_1057);
					add(label_new, c);
					c.gridwidth = 2;
					c.gridy++;
					show_msg = 0;
				}
				/*
				 * прорисовка моих сообщений
				 */
				if (id == myId)
				{
					c.gridwidth = 2;
					c.gridx = 1;
					c.anchor = GridBagConstraints.EAST;
					put_msg(jo, dt, Utilit.COLOR_1085);
				}
				/*
				 * прорисовка сообщений собеседника
				 */
				else
				{
					c.gridwidth = 2;
					c.gridx = 0;
					c.anchor = GridBagConstraints.WEST;
					put_msg(jo, dt, Color.white);
				}
				c.gridy++;
				last_id = jo.getString("id");
			}
			main.revalidate();
			main.repaint();
			if (jarray.length() > 0)
				clip.play();
			JScrollBar bar = loginer.getChat().scroll.getVerticalScrollBar();
			bar.setValue(bar.getMaximum());
		}
	}

	private void delNewMsg(String msg)
	{
		Component[] componentList = getComponents();
		for (Component comp : componentList)
		{
			if (!comp.getClass().getName().toString().equals("javax.swing.JLabel"))
				continue;
			JLabel lbl = (JLabel) comp;
			if (lbl.getName() != null && lbl.getName().equals("new"))
				remove(comp);
		}
	}

	private void put_msg(JSONObject jo, DTime dt, Color color)
	{
		if (jo.getInt("img") == 1)
		{
			ImageManipulation im;
			try
			{
				im = main.store.getChatImg(jo.getInt("id"));
				JPanel imagePanel = im.getImageTxt(300, 300, jo.getString("msg"), dt.time(), 50, color);
				imagePanel.addMouseListener(new ImageMsgListener(main, im, jo.getString("msg")));
				add(imagePanel, c);
			} catch (JSONException | SQLException | IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
		{
			JLabel myJLabel = new JLabel(Smile.replace("<html><br><p  style=\"font-size: 11px;\">&nbsp;&nbsp;"
					+ Utilit.insertWordWrap(jo.getString("msg"), 50, "<br>&nbsp;&nbsp;")
					+ "&nbsp;&nbsp;<br><br><p style=\"font-size: 7px;color: " + Utilit.S_COLOR_1057 + ";\">&nbsp;&nbsp;"
					+ dt.time()));
			myJLabel.setOpaque(true);
			myJLabel.setBackground(color);
			add(myJLabel, c);
		}
	}

	public class ImageMsgListener implements MouseListener
	{
		private ImageManipulation image;
		private Huprum            main;
		private String            msg;

		public ImageMsgListener(Huprum main, ImageManipulation im1, String msg1)
		{
			this.main = main;
			image = im1;
			msg = msg1;
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			try
			{
				image.showSave(main, msg);
			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(main, "Что то с сохранением пошло не так", "Ошибка",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
		}
	}
}