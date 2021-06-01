package huprum.main.chat.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.chat.Chat;
import huprum.main.chat.UserButtomClass;
import huprum.main.connections.Client;
import huprum.main.loginer.Loginer;

public class CenterPanel extends JPanel 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridBagConstraints c;
	private int myId;
	private Huprum main;
	private Loginer loginer;
	
	
	public CenterPanel(Huprum main) {
		this.main = main;
		loginer = main.getLoginer();
		myId = loginer.getId();
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.weightx = 1;		
		c.insets = new Insets(3, 5, 3, 5);

	}
	public void chatRedr() {
		/*
		 *  обращение к серверу
		 */
		
		removeAll();
		
		Client cl = main.getCl();
		Map<String, String> pars = new HashMap<String, String>();
		String otvet = null;
		pars.clear();
		pars.put("action", "get_chat");
		pars.put("myid", loginer.getChat().wp.getMyId());		
		pars.put("id", loginer.getChat().wp.getId());
		try{otvet = cl.send(pars);} catch (IOException e1){e1.printStackTrace();}
		JSONObject jo1 = new JSONObject(otvet);
		JSONArray jarray  = jo1.getJSONArray("chat");
		System.out.println("jarray = " + jarray);
		
		for (int i = 0; i < jarray.length(); i++)
		{			
			JSONObject jo = jarray.getJSONObject(i);
			String sId = (String) jo.get("user1_id");					
			int id =  Integer.parseInt(sId);
			if(id==myId) {
			c.anchor = GridBagConstraints.EAST;
			JLabel myJLabel = new JLabel((String) jo.get("msg"));
			add(myJLabel, c);
			System.out.println("mymsg = " + jo.get("msg"));
			c.gridy++;
			}
			else {
			c.anchor = GridBagConstraints.WEST;
			add(new JLabel((String) jo.get("msg")), c);	
			System.out.println("notmymsg = " + jo.get("msg"));
			c.gridy++;
			}

		}
		main.revalidate();
		main.repaint();
}
}