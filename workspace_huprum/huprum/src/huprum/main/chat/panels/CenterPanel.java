package huprum.main.chat.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.connections.Client;
import huprum.main.loginer.Loginer;
import huprum.main.media.PlaySound;
import huprum.main.utils.DTime;
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
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 5, 5, 5);
		try
		{
			clip = new PlaySound("Sound_16487.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			// TODO Auto-generated catch block
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
			// System.out.println("jarray = " + jarray);
			removeAll();
			c.gridy = 0;
			c.gridwidth = 1;
			for (c.gridx = 0; c.gridx < 3; c.gridx++)
				add(new JLabel("                     "), c);
			c.gridwidth = 2;
			c.gridy++;
			for (int i = 0; i < jarray.length(); i++)
			{
				JSONObject jo  = jarray.getJSONObject(i);
				String     sId = (String) jo.get("user1_id");
				int        id  = Integer.parseInt(sId);
				DTime      dt  = null;
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
				 * прорисовка моих сообщений
				 */
				if (id == myId)
				{
					c.gridx = 1;
					JLabel myJLabel = new JLabel("<html><p  style=\"font-size: 11px\">"
							+ Utilit.InsertPerenos(jo.getString("msg"), 30, "<br>")
							+ "<br><br><p style=\"font-size: 7px\">" + dt.time());
					myJLabel.setOpaque(true);
					myJLabel.setBackground(Utilit.COLOR_1085);
					add(myJLabel, c);
					// System.out.println("mymsg = " + jo.get("msg"));
					c.gridy++;
				}
				/*
				 * прорисовка сообщений собеседника
				 */
				else
				{
					c.gridx = 0;
					JLabel frJLabel = new JLabel("<html><p style=\"font-size: 11px\">"
							+ Utilit.InsertPerenos(jo.getString("msg"), 30, "<br>")
							+ "<br><br><p style=\"font-size: 7px\">" + dt.time());
					frJLabel.setOpaque(true);
					frJLabel.setBackground(Color.white);
					add(frJLabel, c);
					// System.out.println("notmymsg = " + jo.get("msg"));
					c.gridy++;
				}
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
}