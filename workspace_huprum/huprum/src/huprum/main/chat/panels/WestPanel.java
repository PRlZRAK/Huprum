package huprum.main.chat.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import huprum.main.loginer.Loginer;

public class WestPanel extends JPanel
{
	/**
	 * alesharodygin@gmail.com
	 */
	private static final long  serialVersionUID = 1L;
	private GridBagConstraints c;
	private Client             cl;
	private Huprum             main;
	private UserButtomClass[]  batArray;
    private int myId;
    private UserButtomClass lastButton;
    private JSONArray  jarray;
    private Loginer loginer;
    
	public WestPanel(Huprum main)
	{
		this.main = main;
		loginer = main.getLoginer();		
		myId = loginer.getId();
		lastButton=new UserButtomClass("");
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
		redr();
	}

	private void redr()
	{
		removeAll();
		c.gridx = 0;
		c.gridy = 0;
		ActionListener userButtonListener = new UserButtonListener();
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
			String login = (String) jo.get("login");
			String strId = (String) jo.get("id");					
			int id =  Integer.parseInt(strId);
			if(id!=myId) {
			batArray[i] = new UserButtomClass(login);
			batArray[i].setId(id);	
			batArray[i].addActionListener(userButtonListener);
			add(batArray[i], c);
			c.gridy++;
			}
		}
		c.weighty = 1;
		add(new JLabel(), c);
	}
	public class UserButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e)
		{

			lastButton.setSelect(false);
			UserButtomClass button = (UserButtomClass)(e.getSource());
			button.setSelect(true);
			
			Map<String, String> pars = new HashMap<String, String>();
			String otvet = null;
			
			String strMyId = Integer.toString(myId);
			String strId = button.getId().toString();
			pars.clear();
			pars.put("action", "get_chat");
			pars.put("myid", strMyId);
			pars.put("id", strId);
			try{otvet = cl.send(pars);} catch (IOException e1){e1.printStackTrace();}
			JSONObject jo1 = new JSONObject(otvet);
			jarray  = jo1.getJSONArray("chat");
			System.out.println("jarray = " + jarray);
			loginer.getChat().cp.chatRedr();
			
			lastButton=button;
			
	    }
    }
	public JSONArray getJarray()
	{
		return jarray;
	}
}