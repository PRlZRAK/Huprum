package huprum.main.loginer.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.chat.Chat;
import huprum.main.connections.Client;
import huprum.main.loginer.Loginer;
import huprum.main.utils.Utilit;

public class LoginActionListener implements ActionListener
{
	private Huprum main;
	private Client cl;

	public LoginActionListener(Huprum main)
	{
		this.main = main;
		cl=main.getCl();
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Loginer loginer = main.getLoginer();
		String  log     = loginer.getJlogin();
		if (log.equals(""))
		{
			loginer.setErrLog("<html><p color=red>заполнить");
			return;
		} else
			loginer.setErrLog("");
		
		String  pass     = loginer.getJpass();
		if (pass.equals(""))
		{
			loginer.setErrPas("<html><p color=red>заполнить");
			return;
		} else
			loginer.setErrPas("");
		
		int i = Utilit.CheckLogin(log);
		if(i==1) 
		{
			log=Utilit.CleaPhone(log);
		}
		String[] par = new String[] {"mail","phone","login"};
		Map<String, String> pars = new HashMap<String, String>();
		pars.put("action", "login");
		pars.put(par[i],log);
		String otvet;
		try
		{
			otvet = cl.send(pars);
			System.out.println(otvet);
			JSONObject jo = new JSONObject(otvet);
			int status = jo.getInt("status");
			if (status!=0)
			{

				loginer.setErrPas("<html><p color=red>"+jo.getString("msg"));
				return;
			}
			String jpass= (String) jo.get("password");
			if(!jpass.equals(pass))
			{
				loginer.setErrPas("<html><p color=red>неправильный пароль");
				return;
			}
			
				
			int id=jo.getInt("id");
			String login= (String) jo.get("login");
			String phone= (String) jo.get("phone");
			String password= (String) jo.get("password");
			loginer.setPassword(password);
			loginer.setId(id);
			loginer.setPhone(phone);
			loginer.setLogin(login);
			new Chat(main);
			return;
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
}
