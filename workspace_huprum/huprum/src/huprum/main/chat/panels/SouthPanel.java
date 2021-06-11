package huprum.main.chat.panels;

import java.awt.Button;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import huprum.main.Huprum;
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
	/*
	 * отправка сообщения
	 */
	public class Message implements ActionListener
	{		
		@SuppressWarnings("exports")
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			Client cl = main.getCl();
            String msg = vod.getText().trim();
			Map<String, String> pars = new HashMap<String, String>();
            if(!msg.equals("")) {
			pars.put("action", "put_msg");
			pars.put("id_from", myId);
			pars.put("id_to", loginer.getChat().wp.getId());
			pars.put("msg", msg);
			try	{cl.send(pars);} catch (IOException e){e.printStackTrace();}
			vod.setText("");			
            }     
		}
    }
}
