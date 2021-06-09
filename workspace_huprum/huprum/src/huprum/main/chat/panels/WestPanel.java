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
import huprum.main.chat.UserButtomClass;
import huprum.main.connections.Client;
import huprum.main.loginer.Loginer;

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
		lastButton = new UserButtomClass("");
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTH;
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
		String otvet = null;
		try	{otvet = cl.send(pars);}catch (IOException e){e.printStackTrace();}
		removeAll();
		c.gridx = 0;
		c.gridy = 0;
		ActionListener userButtonListener = new UserButtonListener();
		

		JSONObject jo1 = new JSONObject(otvet);
		JSONArray  ja  = jo1.getJSONArray("users");
		batArray = new UserButtomClass[ja.length()];
		for (int i = 0; i < ja.length(); i++)
		{
			JSONObject jo    = ja.getJSONObject(i);
			String     login = (String) jo.get("login");
			sId = (String) jo.get("id");
			int id = Integer.parseInt(sId);
			if (id != myId)
			{
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

	public class UserButtonListener implements ActionListener
	{
		@SuppressWarnings("exports")
		@Override
		public void actionPerformed(ActionEvent e)
		{
			lastButton.setSelect(false);
			UserButtomClass button = (UserButtomClass) (e.getSource());
			button.setSelect(true);
			strMyId = Integer.toString(myId);
			strId = button.getId().toString();
			loginer.getChat().cp.last_id = "-1";
			lastButton = button;
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
}