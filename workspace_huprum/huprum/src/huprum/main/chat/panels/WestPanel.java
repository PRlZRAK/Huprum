package huprum.main.chat.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.chat.Chat;
import huprum.main.chat.UserButtomClass;
import huprum.main.connections.Client;

public class WestPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridBagConstraints c;
	private Client cl;
	private UserButtomClass ubc;
	
	public WestPanel(Huprum main) {
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
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
		otvet=otvet.substring(otvet.indexOf("[") + 1, otvet.indexOf("]"));
		otvet="["+otvet+"]";
		System.out.println(otvet);
		JSONArray ja = new JSONArray(otvet);
		for (int i = 0; i < ja.length(); i++) {
		    JSONObject jo = ja.getJSONObject(i);
		    System.out.println(jo);
		    String login= (String) jo.get("login");
		    ubc = new UserButtomClass(login);
		  
            add(ubc,c);	
            c.gridy ++;
		}
		
}
}