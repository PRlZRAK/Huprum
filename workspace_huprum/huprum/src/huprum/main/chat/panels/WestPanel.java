package huprum.main.chat.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.chat.UserButtomClass;
import huprum.main.connections.Client;

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

	public WestPanel(Huprum main)
	{
		this.main = main;
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		redr();
	}

	private void redr()
	{
		removeAll();
		c.gridx = 0;
		c.gridy = 0;
		cl = main.getCl();
		Map<String, String> pars = new HashMap<String, String>();
		pars.put("action", "get_users");
		String otvet = null;
		try
		{
			otvet = cl.send(pars);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		JSONObject jo1 = new JSONObject(otvet);
		JSONArray  ja  = jo1.getJSONArray("users");
		batArray = new UserButtomClass[ja.length()];
		for (int i = 0; i < ja.length(); i++)
		{
			JSONObject jo = ja.getJSONObject(i);
			System.out.println(jo);
			String login = (String) jo.get("login");
			batArray[i] = new UserButtomClass(login);
			add(batArray[i], c);
			c.gridy++;
		}
	}
}