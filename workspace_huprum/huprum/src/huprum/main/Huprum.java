package huprum.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.json.JSONObject;

import huprum.main.connections.Client;
import huprum.main.loginer.Loginer;
import huprum.main.sqlite.Store;
import huprum.main.utils.DTime;
import huprum.main.utils.Lang;
import huprum.main.utils.Utilit;

public class Huprum extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3256294715807967862L;
	private static final int  DEFAULT_HEIGHT   = 600;
	private static final int  DEFAULT_WIDTH    = 1024;
	private Loginer           loginer;
	private Client            cl;
	private boolean           remember         = false;
	private JSONObject        personalData;
	public Store              store;

	public Huprum(String title) throws MalformedURLException, SQLException
	{
		super(title);
		try
		{
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e)
		{
			JOptionPane.showMessageDialog(null, "Error in a LaF of executable file");
		}
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				configSave();
				userLogoff();
				store.close();
				System.exit(0);
			}
		});
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		Toolkit   kit        = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		setLocation((screenSize.width - DEFAULT_WIDTH) / 2, (screenSize.height - DEFAULT_HEIGHT) / 2);
		cl = new Client(Utilit.SERVER_URL);
		store = new Store(this);
		String lang = store.getParam("lang");
		if (lang == null)
			new Lang(DTime.localeLang());
		else
			new Lang(lang);
		loginer = new Loginer(this);
		setVisible(true);
	}

	public Client getCl()
	{
		return cl;
	}

	public Loginer getLoginer()
	{
		return loginer;
	}

	public void setLoginer(Loginer loginer)
	{
		this.loginer = loginer;
	}

	public void setRemember(boolean remember)
	{
		this.remember = remember;
	}

	public static void main(String[] args)
	{
		try
		{
			new Huprum("Мессенджер Tuktuk");
		} catch (MalformedURLException | SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void userLogoff()
	{
		HashMap<String, String> pars = new HashMap<String, String>();
		pars.put("action", "user_off");
		pars.put("id", loginer.getId() + "");
		try
		{
			cl.send(pars);
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void configSave()
	{
		try
		{
			store.setParam("lang", Lang.getLang());
			store.setParam("width", Integer.toString(getSize().width));
			store.setParam("height", Integer.toString(getSize().height));
			store.setParam("locatx", Integer.toString(getLocation().x));
			store.setParam("locaty", Integer.toString(getLocation().y));
			if (remember)
			{
				store.setParam("remember", "1");
				store.setParam("login", loginer.getLogin());
				store.setParam("password", loginer.getPassword());
			} else
				store.setParam("remember", "0");
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}

	public JSONObject getPersonalData()
	{
		return personalData;
	}

	public void setPersonalData(JSONObject personalData)
	{
		this.personalData = personalData;
	}
}
