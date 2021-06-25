package huprum.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
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
import huprum.main.sqlite.ImageStor;
import huprum.main.loginer.Loginer;
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
	public ImageStor imageStor;

	public Huprum(String title) throws MalformedURLException
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
				imageStor.close();
				System.exit(0);
			}
		});
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		Toolkit   kit        = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		setLocation((screenSize.width - DEFAULT_WIDTH) / 2, (screenSize.height - DEFAULT_HEIGHT) / 2);
		new Lang("ru");
		cl = new Client(Utilit.SERVER_URL);
		imageStor= new ImageStor();
		try
		{
			imageStor.conn(this);
			imageStor.create();
		} catch (ClassNotFoundException | SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		} catch (MalformedURLException e)
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
		JSONObject jo = new JSONObject();
		if (remember)
		{
			jo.put("status", 1);
			jo.put("login", loginer.getLogin());
			jo.put("password", loginer.getPassword());
		} else
			jo.put("status", 0);
		jo.put("width", getSize().width);
		jo.put("height", getSize().height);
		jo.put("locatx", getLocation().x);
		jo.put("locaty", getLocation().y);
		File       f = new File(Utilit.CONFIG);
		FileWriter filewr;
		try
		{
			filewr = new FileWriter(f);
			filewr.write(jo.toString());
			filewr.flush();
			filewr.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
