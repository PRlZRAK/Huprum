package huprum.main;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;

import javax.swing.JFrame;

import huprum.main.chat.Chat;
import huprum.main.connections.Client;
import huprum.main.loginer.Loginer;

public class Huprum extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3256294715807967862L;
	private static final int DEFAULT_HEIGHT = 600;
	private static final int DEFAULT_WIDTH = 1024;
	private Loginer loginer;
	private Client cl;
	

	public Huprum(String title) {
		super(title);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		setLocation((screenSize.width - DEFAULT_WIDTH) / 2, (screenSize.height - DEFAULT_HEIGHT) / 2);
		cl=null;
		 try
		{
			cl = new Client("http://130.61.155.146/huprum/server/index.php");
			 //cl = new Client("http://localhost/huprum/server/index.php");
		} catch (MalformedURLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//  cl = new Client("http://localhost/huprum/server/index.php");
		//loginer = new Loginer(this);
		new Chat(this);
		setVisible(true);
		
	}

	public Client getCl()
	{
		return cl;
	}

	public Loginer getLoginer() { 
		return loginer;
	}

	public static void main(String[] args) {
		new Huprum("Messenger Huprum");
	}
}
