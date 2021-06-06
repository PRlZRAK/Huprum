package huprum.main.chat.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.connections.Client;
import huprum.main.loginer.Loginer;

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
	private JLabel con;
	String last_id;

	public CenterPanel(Huprum main)
	{
		
		this.main = main;
		loginer = main.getLoginer();
		myId = loginer.getId();
		con = new JLabel("");
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.weightx  = 1.0f;
		cl = main.getCl();
		pars = new HashMap<String, String>();
		
	}

	public void chatRedr()
	{
		/*
		 * обращение к серверу
		 */
		if (loginer.getChat().wp.getId() == null)
			
			return;
		// removeAll();
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
			System.out.println("jarray = " + jarray);
			removeAll();
			for (int i = 0; i < jarray.length(); i++)
			{
				JSONObject jo  = jarray.getJSONObject(i);
				String     sId = (String) jo.get("user1_id");
				int        id  = Integer.parseInt(sId);
				if (id == myId)
				{
					c.insets = new Insets(3, 50, 3, 30);	
					c.anchor = GridBagConstraints.EAST;
					JLabel myJLabel = new JLabel("<html><p  style=\"font-size: 11px\">"+jo.get("msg")+"<html></p>");
					myJLabel.setOpaque(true);
					myJLabel.setBackground(new Color(198,246,218));
					add(myJLabel, c);
					System.out.println("mymsg = " + jo.get("msg"));
					c.gridy++;
					
				} else
				{
					c.insets = new Insets(3, 30, 3, 50);	
					c.anchor = GridBagConstraints.WEST;
					JLabel frJLabel = new JLabel("<html><p style=\"font-size: 11px\">"+jo.get("msg")+"<html></p>");
					frJLabel.setOpaque(true);
					frJLabel.setBackground(Color.white);
					add(frJLabel, c);
					System.out.println("notmymsg = " + jo.get("msg"));
					c.gridy++;
				}
				last_id=jo.getString("id");
			}
		} else
		{
			/*
			removeAll();
			String msg = (String) jo1.get("msg");
			c.anchor = GridBagConstraints.CENTER;
			add(new JLabel(msg), c);
			*/
		}
		
		main.revalidate();
		main.repaint();
	}
}