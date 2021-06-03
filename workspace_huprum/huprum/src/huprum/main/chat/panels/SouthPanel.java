package huprum.main.chat.panels;

import java.awt.Button;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.chat.Chat;
import huprum.main.connections.Client;
import huprum.main.loginer.Loginer;

public class SouthPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Loginer loginer;
	private Huprum main;
	private String myId;
	private TextField vod;
	public SouthPanel(Huprum main) {
		this.main=main;
		loginer = main.getLoginer();
		myId = Integer.toString(loginer.getId());
		vod = new TextField(90);
		add(vod);
		Button enter = new Button("Enter");
		enter.addActionListener(new Message());
		add(enter);
		
	}
	public class Message implements ActionListener
	{		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			Client cl = main.getCl();

			Map<String, String> pars = new HashMap<String, String>();
			String otvet = null;
			JSONObject jo;

			pars.put("action", "put_msg");
			pars.put("id_from", myId);
			pars.put("id_to", loginer.getChat().wp.getId());
			pars.put("msg", vod.getText());
			try	{otvet = cl.send(pars);} catch (IOException e){e.printStackTrace();}
			jo = new JSONObject(otvet);
			vod.setText("");
			//loginer.getChat().cp.chatRedr();
		}
    }
}
