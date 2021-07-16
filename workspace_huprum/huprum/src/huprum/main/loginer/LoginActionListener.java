package huprum.main.loginer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.chat.Chat;
import huprum.main.connections.Client;
import huprum.main.timer.Timer;
import huprum.main.utils.DTime;
import huprum.main.utils.Lang;
import huprum.main.utils.Utilit;

public class LoginActionListener implements ActionListener
{
	private Huprum main;
	private Client cl;

	public LoginActionListener(Huprum main)
	{
		this.main = main;
		cl = main.getCl();
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Loginer loginer = main.getLoginer();
		String  log     = loginer.getJlogin();
		if (log.equals(""))
		{
			loginer.setErrLog("<html><p color=red>"+Lang.put("Fill in the field#Заполнить"));
			return;
		} else
			loginer.setErrLog("");
		String pass = loginer.getJpass();
		if (pass.equals(""))
		{
			loginer.setErrPas("<html><p color=red>"+Lang.put("Fill in the field#Заполнить"));
			loginer.getForgotBut().setVisible(true);
			return;			
		} else
			loginer.setErrPas("");
		int i = Utilit.CheckLogin(log);
		if (i == 1)
		{
			log = Utilit.CleaPhone(log);
		}
		String[]            par  = new String[]
		{ "mail", "phone", "login" };
		Map<String, String> pars = new HashMap<String, String>();
		pars.put("action", "login");
		pars.put(par[i], log);
		pars.put("dtime", DTime.now());
		String otvet;
		try
		{
			otvet = cl.send(pars);
			JSONObject jo     = new JSONObject(otvet);
			int        status = jo.getInt("status");
			if (status != 0)
			{
				loginer.setErrLog("<html><p color=red>" + Lang.put(jo.getString("msg")));
				return;
			}
			String jpass = (String) jo.get("password");
			if (!jpass.equals(pass))
			{
				loginer.setErrPas("<html><p color=red>"+Lang.put("Incorrect password#Неправильный пароль"));
				loginer.getForgotBut().setVisible(true);
				return;
			}
			main.setRemember(loginer.getJremember().isSelected());
			
			int    id       = jo.getInt("id");
			main.getLoginer().setId(id);
			String login    = (String) jo.get("login");
			String phone    = (String) jo.get("phone");
			String password = (String) jo.get("password");
			loginer.setPassword(password);
			loginer.setId(id);
			loginer.setPhone(phone);
			loginer.setLogin(login);
			main.setPersonalData(jo); // для личного кабинета
			Timer.start("чат");
			Chat chat = new Chat(main);
			loginer.setChat(chat);
			Timer.time();
			return;
		} catch (IOException e)
		{
			loginer.setEr_сonnection("<html><p color=red>"+Lang.put("Connection to the server is not established#Нет соединения с сервером"));
			return;
		}
	}
}
